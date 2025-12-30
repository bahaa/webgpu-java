package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.ComputePipeline;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuComputePipelineRelease;
import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuComputePipelineSetLabel;

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

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuComputePipelineSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
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
