package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.IndexFormat;
import io.github.bahaa.webgpu.api.model.RenderBundleDescriptor;
import io.github.bahaa.webgpu.api.model.StringView;
import org.jspecify.annotations.Nullable;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class RenderBundleEncoderImpl extends ObjectBaseImpl implements RenderBundleEncoder {

    protected RenderBundleEncoderImpl(final MemorySegment pointer) {
        super(pointer);
    }

    public static RenderBundleEncoder from(final MemorySegment pointer) {
        return new RenderBundleEncoderImpl(pointer);
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuRenderBundleEncoderSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public void draw(final int vertexCount, final int instanceCount, final int firstVertex, final int firstInstance) {
        wgpuRenderBundleEncoderDraw(pointer(), vertexCount, instanceCount, firstVertex, firstInstance);
    }

    @Override
    public void drawIndexed(final int indexCount, final int instanceCount,
                            final int firstIndex, final int baseVertex, final int firstInstance) {
        wgpuRenderBundleEncoderDrawIndexed(this.pointer(),
                indexCount, instanceCount, firstIndex, baseVertex, firstInstance);
    }

    @Override
    public void drawIndexedIndirect(final Buffer indirectBuffer, final long indirectOffset) {
        wgpuRenderBundleEncoderDrawIndexedIndirect(this.pointer(), indirectBuffer.pointer(), indirectOffset);
    }

    @Override
    public void drawIndirect(final Buffer indirectBuffer, final long indirectOffset) {
        wgpuRenderBundleEncoderDrawIndirect(this.pointer(), indirectBuffer.pointer(), indirectOffset);
    }

    @Override
    public RenderBundle finish(@Nullable final RenderBundleDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var bundle = wgpuRenderBundleEncoderFinish(this.pointer(), descriptor == null ? MemorySegment.NULL :
                    descriptor.toSegmentAddress(arena));
            assertObject(bundle, "wgpuRenderBundleEncoderFinish");
            return RenderBundleImpl.from(bundle);
        }
    }

    @Override
    public void insertDebugMarker(final String markerLabel) {
        try (final var arena = Arena.ofConfined()) {
            wgpuRenderBundleEncoderInsertDebugMarker(this.pointer(), StringView.from(markerLabel).toSegment(arena));
        }
    }

    @Override
    public void popDebugGroup() {
        wgpuRenderBundleEncoderPopDebugGroup(this.pointer());
    }

    @Override
    public void pushDebugGroup(final String groupLabel) {
        try (final var arena = Arena.ofConfined()) {
            wgpuRenderBundleEncoderPushDebugGroup(this.pointer(), StringView.from(groupLabel).toSegment(arena));
        }
    }

    @Override
    public void setBindGroup(final int groupIndex, @Nullable final BindGroup group, final long[] dynamicOffsets) {
        try (final var arena = Arena.ofConfined()) {
            final var segment = arena.allocate(ValueLayout.JAVA_LONG, dynamicOffsets.length);
            segment.copyFrom(MemorySegment.ofArray(dynamicOffsets));
            wgpuRenderBundleEncoderSetBindGroup(this.pointer(), groupIndex,
                    group == null ? MemorySegment.NULL : group.pointer(), dynamicOffsets.length, segment);
        }
    }

    @Override
    public void setIndexBuffer(final Buffer buffer, final IndexFormat format, final long offset, final long size) {
        wgpuRenderBundleEncoderSetIndexBuffer(this.pointer(), buffer.pointer(), format.value(), offset, size);
    }

    @Override
    public void setPipeline(final RenderPipeline pipeline) {
        wgpuRenderBundleEncoderSetPipeline(this.pointer(), pipeline.pointer());
    }

    @Override
    public void setVertexBuffer(final int slot, @Nullable final Buffer buffer, final long offset, final long size) {
        wgpuRenderBundleEncoderSetVertexBuffer(this.pointer(), slot,
                buffer == null ? MemorySegment.NULL : buffer.pointer(), offset, size);
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuRenderBundleEncoderRelease(pointer);
        }
    }
}
