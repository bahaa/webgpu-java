package io.github.bahaa.webgpu.api;


import io.github.bahaa.webgpu.api.model.Color;
import io.github.bahaa.webgpu.api.model.IndexFormat;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface RenderPassEncoder extends ObjectBase {

    void setPipeline(final RenderPipeline pipeline);

    void draw(final int vertexCount, final int instanceCount, final int firstVertex, final int firstInstance);

    default void draw(final int vertexCount) {
        draw(vertexCount, 1, 0, 0);
    }

    void drawIndexed(int indexCount, int instanceCount, int firstIndex, int baseVertex, int firstInstance);

    void drawIndexedIndirect(Buffer indirectBuffer, int indirectOffset);

    void setVertexBuffer(final int slot, @Nullable final Buffer buffer, final long offset, final long size);

    default void setVertexBuffer(final int slot, final Buffer buffer) {
        setVertexBuffer(slot, buffer, 0, buffer.size());
    }

    void setBindGroup(final int groupIndex, final BindGroup bindGroup);

    void beginOcclusionQuery(int queryIndex);

    void endOcclusionQuery();

    void executeBundles(List<RenderBundle> bundles);

    void end();

    void insertDebugMarker(String markerLabel);

    void popDebugGroup();

    void pushDebugGroup(String groupLabel);

    void setBlendConstant(Color color);

    void setIndexBuffer(Buffer buffer, IndexFormat format, long offset, long size);

    void setScissorRect(int x, int y, int width, int height);

    void setStencilReference(int reference);

    void setViewport(float x, float y, float width, float height, float minDepth, float maxDepth);
}
