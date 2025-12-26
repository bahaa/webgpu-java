package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.BindGroup;
import io.github.bahaa.webgpu.api.ComputePassEncoder;
import io.github.bahaa.webgpu.api.ComputePipeline;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class ComputePassEncoderImpl extends ObjectBaseImpl implements ComputePassEncoder {
    protected ComputePassEncoderImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static ComputePassEncoderImpl from(final MemorySegment pointer) {
        return new ComputePassEncoderImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public void setPipeline(final ComputePipeline pipeline) {
        wgpuComputePassEncoderSetPipeline(pointer(), pipeline.pointer());
    }

    @Override
    public void setBindGroup(final int groupIndex, final BindGroup bindGroup) {
        // TODO
        wgpuComputePassEncoderSetBindGroup(pointer(), groupIndex, bindGroup.pointer(), 0, MemorySegment.NULL);
    }

    @Override
    public void dispatchWorkgroups(final int workgroupCountX, final int workgroupCountY, final int workgroupCountZ) {
        wgpuComputePassEncoderDispatchWorkgroups(pointer(), workgroupCountX, workgroupCountY, workgroupCountZ);
    }

    @Override
    public void end() {
        wgpuComputePassEncoderEnd(this.pointer());
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuComputePassEncoderSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuComputePassEncoderRelease(pointer);
        }
    }
}
