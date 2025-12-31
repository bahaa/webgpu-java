package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.IndexFormat;
import io.github.bahaa.webgpu.api.model.RenderBundleDescriptor;
import org.jspecify.annotations.Nullable;

public interface RenderBundleEncoder extends ObjectBase {

    void draw(int vertexCount, int instanceCount, int firstVertex, int firstInstance);

    void drawIndexed(int indexCount, int instanceCount, int firstIndex, int baseVertex, int firstInstance);

    void drawIndexedIndirect(Buffer indirectBuffer, long indirectOffset);

    void drawIndirect(Buffer indirectBuffer, long indirectOffset);

    RenderBundle finish(@Nullable RenderBundleDescriptor descriptor);

    void insertDebugMarker(String markerLabel);

    void popDebugGroup();

    void pushDebugGroup(String groupLabel);

    void setBindGroup(int groupIndex, @Nullable BindGroup group, long[] dynamicOffsets);

    void setIndexBuffer(Buffer buffer, IndexFormat format, long offset, long size);

    void setPipeline(RenderPipeline pipeline);

    void setVertexBuffer(int slot, @Nullable Buffer buffer, long offset, long size);
}
