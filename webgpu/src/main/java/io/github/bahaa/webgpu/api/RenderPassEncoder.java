package io.github.bahaa.webgpu.api;


public interface RenderPassEncoder extends ObjectBase {

    void setPipeline(final RenderPipeline pipeline);

    void draw(final int vertexCount, final int instanceCount, final int firstVertex, final int firstInstance);

    default void draw(final int vertexCount) {
        draw(vertexCount, 1, 0, 0);
    }

    void setVertexBuffer(final int slot, final Buffer buffer, final long offset, final long size);

    default void setVertexBuffer(final int slot, final Buffer buffer) {
        setVertexBuffer(slot, buffer, 0, buffer.size());
    }

    void setBindGroup(final int groupIndex, final BindGroup bindGroup);

    void end();
}
