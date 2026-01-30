package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Adapter;
import io.github.bahaa.webgpu.api.Instance;
import io.github.bahaa.webgpu.api.Surface;
import io.github.bahaa.webgpu.api.WebGpuException;
import io.github.bahaa.webgpu.api.model.InstanceDescriptor;
import io.github.bahaa.webgpu.api.model.RequestAdapterOptions;
import io.github.bahaa.webgpu.api.model.SurfaceDescriptor;
import io.github.bahaa.webgpu.ffm.WGPURequestAdapterCallback;
import io.github.bahaa.webgpu.ffm.WGPURequestAdapterCallbackInfo;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

public class InstanceImpl extends NativeObjectImpl implements Instance {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private InstanceImpl(final MemorySegment pointer) {
        super(pointer);
    }

    public static Instance create(final InstanceDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var pointer =
                    wgpuCreateInstance(descriptor == null ? MemorySegment.NULL : descriptor.toSegment(arena));
            assertObject(pointer, "wgpuCreateInstance");
            return new InstanceImpl(pointer);
        }

    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public CompletableFuture<Adapter> requestAdapter(final RequestAdapterOptions options) {
        final var arena = Arena.ofAuto();
        final var future = new CompletableFuture<Adapter>();

        final WGPURequestAdapterCallback.Function callback = (status, adapter, message,
                                                              userdata1, userdata2) -> {
            if (status == WGPURequestAdapterStatus_Success()) {
                future.complete(AdapterImpl.from(adapter, this));
            } else {
                future.completeExceptionally(new WebGpuException("WGPURequestAdapterStatus: %d"
                        .formatted(status)));
            }
        };

        final var callbackStub = WGPURequestAdapterCallback.allocate(callback, arena);

        final var callbackInfo = WGPURequestAdapterCallbackInfo.allocate(arena);
        WGPURequestAdapterCallbackInfo.callback(callbackInfo, callbackStub);
        WGPURequestAdapterCallbackInfo.mode(callbackInfo, CallbackMode.ALLOW_PROCESS_EVENTS.value());

        var _ = wgpuInstanceRequestAdapter(arena, this.pointer(), options.toSegment(arena), callbackInfo);
        scheduleFuturePoller();

        return future;
    }

    @Override
    public Surface createSurface(final SurfaceDescriptor surfaceDescriptor) {
        try (final var arena = Arena.ofConfined()) {
            return SurfaceImpl.from(wgpuInstanceCreateSurface(pointer(), surfaceDescriptor.toSegment(arena)));
        }
    }

    @Override
    public void close() {
        super.close();
        this.executor.shutdown();
    }

    void scheduleFuturePoller() {
        this.executor.execute(() -> {
            wgpuInstanceProcessEvents(this.pointer());
        });
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuInstanceRelease(pointer);
        }
    }
}
