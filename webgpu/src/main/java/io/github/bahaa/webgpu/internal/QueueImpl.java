package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Buffer;
import io.github.bahaa.webgpu.api.CommandBuffer;
import io.github.bahaa.webgpu.api.Queue;
import io.github.bahaa.webgpu.api.model.Extent3D;
import io.github.bahaa.webgpu.api.model.TexelCopyBufferLayout;
import io.github.bahaa.webgpu.api.model.TexelCopyTextureInfo;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class QueueImpl extends ObjectBaseImpl implements Queue {
    protected QueueImpl(final MemorySegment pointer) {
        super(pointer);
    }

    public static Queue from(final MemorySegment pointer) {
        return new QueueImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public void submit(final List<CommandBuffer> commands) {
        try (final var arena = Arena.ofConfined()) {
            final var commandArray = arena.allocate(ValueLayout.ADDRESS, commands.size());
            for (var i = 0; i < commands.size(); i++) {
                commandArray.setAtIndex(ValueLayout.ADDRESS, i, commands.get(i).pointer());
            }
            wgpuQueueSubmit(this.pointer(), commands.size(), commandArray);
        }
    }

    @Override
    public void writeBuffer(final Buffer buffer, final long bufferOffset, final MemorySegment data, final long size) {
        wgpuQueueWriteBuffer(pointer(), buffer.pointer(), bufferOffset, data, size);
    }

    @Override
    public void writeTexture(final TexelCopyTextureInfo destination, final MemorySegment data, final long dataSize,
                             final TexelCopyBufferLayout dataLayout, final Extent3D writeSize) {
        try (final var arena = Arena.ofConfined()) {
            wgpuQueueWriteTexture(this.pointer(), destination.toSegmentAddress(arena), data, dataSize,
                    dataLayout.toSegmentAddress(arena), writeSize.toSegmentAddress(arena));
        }
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuQueueRelease(pointer);
        }
    }
}
