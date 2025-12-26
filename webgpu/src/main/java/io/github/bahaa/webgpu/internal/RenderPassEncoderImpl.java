package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.Color;
import io.github.bahaa.webgpu.api.model.IndexFormat;
import io.github.bahaa.webgpu.api.model.StringView;
import org.jspecify.annotations.Nullable;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;

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
    public void drawIndexed(final int indexCount, final int instanceCount, final int firstIndex, final int baseVertex,
                            final int firstInstance) {
        wgpuRenderPassEncoderDrawIndexed(pointer(), indexCount, instanceCount, firstIndex, baseVertex, firstInstance);
    }

    @Override
    public void drawIndexedIndirect(final Buffer indirectBuffer, final int indirectOffset) {
        wgpuRenderPassEncoderDrawIndirect(this.pointer(), indirectBuffer.pointer(), indirectOffset);
    }

    @Override
    public void setVertexBuffer(final int slot, @Nullable final Buffer buffer, final long offset, final long size) {
        wgpuRenderPassEncoderSetVertexBuffer(pointer(), slot,
                buffer == null ? MemorySegment.NULL : buffer.pointer(), offset, size);
    }

    @Override
    public void setBindGroup(final int groupIndex, final BindGroup bindGroup) {
        // TODO
        wgpuRenderPassEncoderSetBindGroup(pointer(), groupIndex, bindGroup.pointer(), 0, MemorySegment.NULL);
    }

    @Override
    public void beginOcclusionQuery(final int queryIndex) {
        wgpuRenderPassEncoderBeginOcclusionQuery(pointer(), queryIndex);
    }

    @Override
    public void endOcclusionQuery() {
        wgpuRenderPassEncoderEndOcclusionQuery(pointer());
    }

    @Override
    public void executeBundles(final List<RenderBundle> bundles) {
        try (final var arena = Arena.ofConfined()) {
            final var struct = arena.allocate(ValueLayout.ADDRESS, bundles.size());
            for (var i = 0; i < bundles.size(); i++) {
                struct.setAtIndex(ValueLayout.ADDRESS, i, bundles.get(i).pointer());
            }
            wgpuRenderPassEncoderExecuteBundles(this.pointer(), bundles.size(),
                    MemorySegment.ofAddress(struct.address()));
        }
    }

    @Override
    public void end() {
        wgpuRenderPassEncoderEnd(this.pointer());
    }

    @Override
    public void insertDebugMarker(final String markerLabel) {
        try (final var arena = Arena.ofConfined()) {
            wgpuRenderPassEncoderInsertDebugMarker(this.pointer(), StringView.from(markerLabel).toSegment(arena));
        }
    }

    @Override
    public void popDebugGroup() {
        wgpuRenderPassEncoderPopDebugGroup(this.pointer());
    }

    @Override
    public void pushDebugGroup(final String groupLabel) {
        try (final var arena = Arena.ofConfined()) {
            wgpuRenderPassEncoderPushDebugGroup(this.pointer(), StringView.from(groupLabel).toSegment(arena));
        }
    }

    @Override
    public void setBlendConstant(final Color color) {
        try (final var arena = Arena.ofConfined()) {
            wgpuRenderPassEncoderSetBlendConstant(this.pointer(), color.toSegmentAddress(arena));
        }
    }

    @Override
    public void setIndexBuffer(final Buffer buffer, final IndexFormat format, final long offset, final long size) {
        wgpuRenderPassEncoderSetIndexBuffer(this.pointer(), buffer.pointer(), format.value(), offset, size);
    }

    @Override
    public void setScissorRect(final int x, final int y, final int width, final int height) {
        wgpuRenderPassEncoderSetScissorRect(this.pointer(), x, y, width, height);
    }

    @Override
    public void setStencilReference(final int reference) {
        wgpuRenderPassEncoderSetStencilReference(this.pointer(), reference);
    }

    @Override
    public void setViewport(final float x, final float y, final float width, final float height,
                            final float minDepth, final float maxDepth) {
        wgpuRenderPassEncoderSetViewport(this.pointer(), x, y, width, height, minDepth, maxDepth);
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
