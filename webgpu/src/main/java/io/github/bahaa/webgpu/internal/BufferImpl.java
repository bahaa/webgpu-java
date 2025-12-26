package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Buffer;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class BufferImpl extends ObjectBaseImpl implements Buffer {

    protected BufferImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static Buffer from(final MemorySegment pointer) {
        return new BufferImpl(pointer);
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
    public void unmap() {
        wgpuBufferUnmap(this.pointer());
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
