package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.CommandBuffer;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuCommandBufferRelease;

class CommandBufferImpl extends ObjectBaseImpl implements CommandBuffer {

    protected CommandBufferImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static CommandBufferImpl from(final MemorySegment pointer) {
        return new CommandBufferImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuCommandBufferRelease(pointer);
        }
    }
}
