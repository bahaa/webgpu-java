package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Buffer;
import io.github.bahaa.webgpu.api.WebGpuException;
import io.github.bahaa.webgpu.api.model.MapMode;
import io.github.bahaa.webgpu.api.model.StringView;
import io.github.bahaa.webgpu.ffm.WGPUBufferMapCallback;
import io.github.bahaa.webgpu.ffm.WGPUBufferMapCallbackInfo;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class BufferImpl extends ObjectBaseImpl implements Buffer {

    protected final DeviceImpl device;

    protected BufferImpl(final MemorySegment pointer, final DeviceImpl device) {
        super(pointer);
        this.device = device;
    }

    static Buffer from(final MemorySegment pointer, final DeviceImpl device) {
        return new BufferImpl(pointer, device);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public long size() {
        return wgpuBufferGetSize(pointer());
    }

    @Override
    public MemorySegment getMappedRange(final long offset, final long size) {
        final var buf = wgpuBufferGetMappedRange(this.pointer(), offset, size);
        return buf.asSlice(0, size);
    }

    @Override
    public CompletableFuture<Void> mapAsync(final EnumSet<MapMode> mode, final long offset, final long size) {
        final var future = new CompletableFuture<Void>();

        final WGPUBufferMapCallback.Function callback = (status, message, userdata1, userdata2) -> {
            if (status == WGPUMapAsyncStatus_Success()) {
                future.complete(null);
            } else {
                future.completeExceptionally(new WebGpuException("mapAsync failed with status %d".formatted(status)));
            }
        };

        try (final var arena = Arena.ofConfined()) {
            final var stub = WGPUBufferMapCallback.allocate(callback, arena);
            final var info = WGPUBufferMapCallbackInfo.allocate(arena);
            WGPUBufferMapCallbackInfo.callback(info, stub);
            WGPUBufferMapCallbackInfo.callback(info, stub);

            final var modeFlags = mode.stream()
                    .mapToLong(MapMode::value)
                    .reduce(0, (a, b) -> a | b);

            var _ = wgpuBufferMapAsync(arena, this.pointer(), modeFlags, offset, size, info);

            this.device.poll(true);
        }

        return future;
    }

    @Override
    public void unmap() {
        wgpuBufferUnmap(this.pointer());
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuBufferSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuBufferRelease(pointer);
        }
    }
}
