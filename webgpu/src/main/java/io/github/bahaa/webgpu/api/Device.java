package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.*;
import org.jspecify.annotations.Nullable;

public interface Device extends ObjectBase {

    Queue queue();

    ShaderModule createShaderModule(final ShaderModuleDescriptor descriptor);

    PipelineLayout createPipelineLayout(final PipelineLayoutDescriptor descriptor);

    RenderPipeline createRenderPipeline(final RenderPipelineDescriptor descriptor);

    ComputePipeline createComputePipeline(final ComputePipelineDescriptor descriptor);

    CommandEncoder createCommandEncoder(final CommandEncoderDescriptor descriptor);

    Buffer createBuffer(final BufferDescriptor descriptor);

    BindGroupLayout createBindGroupLayout(final BindGroupLayoutDescriptor descriptor);

    BindGroup createBindGroup(final BindGroupDescriptor descriptor);

    Texture createTexture(final TextureDescriptor descriptor);

    Sampler createSampler(final @Nullable SamplerDescriptor descriptor);

    RenderBundleEncoder createRenderBundleEncoder(final RenderBundleEncoderDescriptor descriptor);

    QuerySet createQuerySet(final QuerySetDescriptor descriptor);
}
