package io.github.bahaa.webgpu.api;


import io.github.bahaa.webgpu.api.model.CommandBufferDescriptor;
import io.github.bahaa.webgpu.api.model.ComputePassDescriptor;
import io.github.bahaa.webgpu.api.model.RenderPassDescriptor;

public interface CommandEncoder extends ObjectBase {

    RenderPassEncoder beginRenderPass(final RenderPassDescriptor descriptor);

    ComputePassEncoder beginComputePass(final ComputePassDescriptor descriptor);

    CommandBuffer finish(final CommandBufferDescriptor descriptor);
}
