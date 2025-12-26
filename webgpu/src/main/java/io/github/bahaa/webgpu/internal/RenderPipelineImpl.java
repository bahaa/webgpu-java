package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.BindGroupLayout;
import io.github.bahaa.webgpu.api.RenderPipeline;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuRenderPipelineGetBindGroupLayout;
import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuRenderPipelineRelease;

class RenderPipelineImpl extends ObjectBaseImpl implements RenderPipeline {

    protected RenderPipelineImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static RenderPipelineImpl from(final MemorySegment pointer) {
        return new RenderPipelineImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public BindGroupLayout getBindGroupLayout(final int groupIndex) {
        final var group = wgpuRenderPipelineGetBindGroupLayout(pointer(), groupIndex);
        assertObject(group, "wgpuRenderPipelineGetBindGroupLayout");
        return BindGroupLayoutImpl.from(group);
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuRenderPipelineRelease(pointer);
        }
    }
}
