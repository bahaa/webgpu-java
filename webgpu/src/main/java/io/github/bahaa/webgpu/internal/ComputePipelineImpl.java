package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.ComputePipeline;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuComputePipelineRelease;

class ComputePipelineImpl extends ObjectBaseImpl implements ComputePipeline {

    protected ComputePipelineImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static ComputePipelineImpl from(final MemorySegment pointer) {
        return new ComputePipelineImpl(pointer);
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
            wgpuComputePipelineRelease(pointer);
        }
    }
}
