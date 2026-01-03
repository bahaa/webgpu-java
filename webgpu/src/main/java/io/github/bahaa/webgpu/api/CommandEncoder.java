package io.github.bahaa.webgpu.api;


import io.github.bahaa.webgpu.api.model.*;

public interface CommandEncoder extends ObjectBase {

    RenderPassEncoder beginRenderPass(final RenderPassDescriptor descriptor);

    ComputePassEncoder beginComputePass(final ComputePassDescriptor descriptor);

    CommandBuffer finish(final CommandBufferDescriptor descriptor);

    default CommandBuffer finish() {
        return finish(CommandBufferDescriptor.create());
    }

    void clearBuffer(Buffer buffer, long offset, long size);

    void copyBufferToBuffer(Buffer source, long sourceOffset, Buffer destination, long destinationOffset, long size);

    void copyBufferToTexture(TexelCopyBufferInfo source, TexelCopyTextureInfo destination, Extent3D copySize);

    void copyTextureToBuffer(TexelCopyTextureInfo source, TexelCopyBufferInfo destination, Extent3D copySize);

    void copyTextureToTexture(TexelCopyTextureInfo source, TexelCopyTextureInfo destination, Extent3D copySize);

    void insertDebugMarker(String markerLabel);

    void popDebugGroup();

    void pushDebugGroup(String groupLabel);

    void resolveQuerySet(QuerySet querySet, int firstQuery, int queryCount, Buffer destination, long destinationOffset);

    void writeTimestamp(QuerySet querySet, int queryIndex);
}
