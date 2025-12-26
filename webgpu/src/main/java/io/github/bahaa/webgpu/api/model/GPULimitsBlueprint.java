package io.github.bahaa.webgpu.api.model;

import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface GPULimitsBlueprint extends StructBlueprint {

    int maxTextureDimension1D();

    int maxTextureDimension2D();

    int maxTextureDimension3D();

    int maxTextureArrayLayers();

    int maxBindGroups();

    int maxBindGroupsPlusVertexBuffers();

    int maxBindingsPerBindGroup();

    int maxDynamicUniformBuffersPerPipelineLayout();

    int maxDynamicStorageBuffersPerPipelineLayout();

    int maxSampledTexturesPerShaderStage();

    int maxSamplersPerShaderStage();

    int maxStorageBuffersPerShaderStage();

    int maxStorageTexturesPerShaderStage();

    int maxUniformBuffersPerShaderStage();

    long maxUniformBufferBindingSize();

    long maxStorageBufferBindingSize();

    int minUniformBufferOffsetAlignment();

    int minStorageBufferOffsetAlignment();

    int maxVertexBuffers();

    long maxBufferSize();

    int maxVertexAttributes();

    int maxVertexBufferArrayStride();

    int maxInterStageShaderVariables();

    int maxColorAttachments();

    int maxColorAttachmentBytesPerSample();

    int maxComputeWorkgroupStorageSize();

    int maxComputeInvocationsPerWorkgroup();

    int maxComputeWorkgroupSizeX();

    int maxComputeWorkgroupSizeY();

    int maxComputeWorkgroupSizeZ();

    int maxComputeWorkgroupsPerDimension();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
