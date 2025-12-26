package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.PipelineLayout;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuPipelineLayoutRelease;

class PipelineLayoutImpl extends ObjectBaseImpl implements PipelineLayout {

    protected PipelineLayoutImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static PipelineLayoutImpl from(final MemorySegment pointer) {
        return new PipelineLayoutImpl(pointer);
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
            wgpuPipelineLayoutRelease(pointer);
        }
    }
}
