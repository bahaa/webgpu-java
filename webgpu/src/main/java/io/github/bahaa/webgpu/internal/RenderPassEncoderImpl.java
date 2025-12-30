package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.BindGroup;
import io.github.bahaa.webgpu.api.Buffer;
import io.github.bahaa.webgpu.api.RenderPassEncoder;
import io.github.bahaa.webgpu.api.RenderPipeline;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class RenderPassEncoderImpl extends ObjectBaseImpl implements RenderPassEncoder {

    protected RenderPassEncoderImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static RenderPassEncoder from(final MemorySegment pointer) {
        return new RenderPassEncoderImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public void setPipeline(final RenderPipeline pipeline) {
        wgpuRenderPassEncoderSetPipeline(pointer(), pipeline.pointer());
    }

    @Override
    public void draw(final int vertexCount, final int instanceCount, final int firstVertex, final int firstInstance) {
        wgpuRenderPassEncoderDraw(pointer(), vertexCount, instanceCount, firstVertex, firstInstance);
    }

    @Override
    public void setVertexBuffer(final int slot, final Buffer buffer, final long offset, final long size) {
        wgpuRenderPassEncoderSetVertexBuffer(pointer(), slot, buffer.pointer(), offset, size);
    }

    @Override
    public void setBindGroup(final int groupIndex, final BindGroup bindGroup) {
        // TODO
        wgpuRenderPassEncoderSetBindGroup(pointer(), groupIndex, bindGroup.pointer(), 0, MemorySegment.NULL);
    }

    @Override
    public void end() {
        wgpuRenderPassEncoderEnd(this.pointer());
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuRenderPassEncoderSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuRenderPassEncoderRelease(pointer);
        }
    }
}
