package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.*;
import org.jspecify.annotations.Nullable;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class DeviceImpl extends ObjectBaseImpl implements Device {
    protected DeviceImpl(final MemorySegment pointer) {
        super(pointer);
    }

    public static DeviceImpl from(final MemorySegment pointer) {
        return new DeviceImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public Queue queue() {
        return QueueImpl.from(wgpuDeviceGetQueue(this.pointer()));
    }

    @Override
    public ShaderModule createShaderModule(final ShaderModuleDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var shaderModule = wgpuDeviceCreateShaderModule(this.pointer(), descriptor.toSegment(arena));
            assertObject(shaderModule, "wgpuDeviceCreateShaderModule");
            return ShaderModuleImpl.from(shaderModule);
        }
    }

    @Override
    public PipelineLayout createPipelineLayout(final PipelineLayoutDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var pipelineLayout = wgpuDeviceCreatePipelineLayout(this.pointer(), descriptor.toSegment(arena));
            assertObject(pipelineLayout, "wgpuDeviceCreatePipelineLayout");
            return PipelineLayoutImpl.from(pipelineLayout);
        }
    }

    @Override
    public RenderPipeline createRenderPipeline(final RenderPipelineDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var pipeline = wgpuDeviceCreateRenderPipeline(this.pointer(), descriptor.toSegment(arena));
            assertObject(pipeline, "wgpuDeviceCreateRenderPipeline");
            return RenderPipelineImpl.from(pipeline);
        }
    }

    @Override
    public ComputePipeline createComputePipeline(final ComputePipelineDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var pipeline = wgpuDeviceCreateComputePipeline(this.pointer(), descriptor.toSegment(arena));
            assertObject(pipeline, "wgpuDeviceCreateComputePipeline");
            return ComputePipelineImpl.from(pipeline);
        }
    }

    @Override
    public CommandEncoder createCommandEncoder(final CommandEncoderDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var encoder = wgpuDeviceCreateCommandEncoder(this.pointer(), descriptor.toSegment(arena));
            assertObject(encoder, "wgpuDeviceCreateCommandEncoder");
            return CommandEncoderImpl.from(encoder);
        }
    }

    @Override
    public Buffer createBuffer(final BufferDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var buffer = wgpuDeviceCreateBuffer(this.pointer(), descriptor.toSegment(arena));
            assertObject(buffer, "wgpuDeviceCreateBuffer");
            return BufferImpl.from(buffer);
        }
    }

    @Override
    public BindGroupLayout createBindGroupLayout(final BindGroupLayoutDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var layout = wgpuDeviceCreateBindGroupLayout(this.pointer(), descriptor.toSegment(arena));
            assertObject(layout, "wgpuDeviceCreateBindGroupLayout");
            return BindGroupLayoutImpl.from(layout);
        }
    }

    @Override
    public BindGroup createBindGroup(final BindGroupDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var bindGroup = wgpuDeviceCreateBindGroup(this.pointer(), descriptor.toSegment(arena));
            assertObject(bindGroup, "wgpuDeviceCreateBindGroup");
            return BindGroupImpl.from(bindGroup);
        }
    }

    @Override
    public Texture createTexture(final TextureDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var texture = wgpuDeviceCreateTexture(this.pointer(), descriptor.toSegment(arena));
            assertObject(texture, "wgpuDeviceCreateTexture");
            return TextureImpl.from(texture);
        }
    }

    @Override
    public Sampler createSampler(@Nullable final SamplerDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var texture = wgpuDeviceCreateSampler(this.pointer(), descriptor == null ? MemorySegment.NULL :
                    descriptor.toSegment(arena));
            assertObject(texture, "wgpuDeviceCreateSampler");
            return SamplerImpl.from(texture);
        }
    }

    @Override
    public RenderBundleEncoder createRenderBundleEncoder(final RenderBundleEncoderDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var texture = wgpuDeviceCreateRenderBundleEncoder(this.pointer(), descriptor.toSegment(arena));
            assertObject(texture, "wgpuDeviceCreateRenderBundleEncoder");
            return RenderBundleEncoderImpl.from(texture);
        }
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuDeviceSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuDeviceRelease(pointer);
        }
    }
}
