package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.Adapter;
import io.github.bahaa.webgpu.ffm.WGPULimits;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

/// The [SupportedLimits] object of the WebGPU API describes the limits supported by an
///  [io.github.bahaa.webgpu.api.Adapter].
///
/// The [SupportedLimits] object for the current adapter is accessed via the
///  [Adapter#limits()] property.
@Prototype.Blueprint
interface SupportedLimitsBlueprint extends StructBlueprint {

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
        final var struct = WGPULimits.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPULimits.maxTextureDimension1D(struct, maxTextureDimension1D());
        WGPULimits.maxTextureDimension2D(struct, maxTextureDimension2D());
        WGPULimits.maxTextureDimension3D(struct, maxTextureDimension3D());
        WGPULimits.maxTextureArrayLayers(struct, maxTextureArrayLayers());
        WGPULimits.maxBindGroups(struct, maxBindGroups());
        WGPULimits.maxBindGroupsPlusVertexBuffers(struct, maxBindGroupsPlusVertexBuffers());
        WGPULimits.maxBindingsPerBindGroup(struct, maxBindingsPerBindGroup());
        WGPULimits.maxDynamicUniformBuffersPerPipelineLayout(struct, maxDynamicUniformBuffersPerPipelineLayout());
        WGPULimits.maxDynamicStorageBuffersPerPipelineLayout(struct, maxDynamicStorageBuffersPerPipelineLayout());
        WGPULimits.maxSampledTexturesPerShaderStage(struct, maxSampledTexturesPerShaderStage());
        WGPULimits.maxSamplersPerShaderStage(struct, maxSamplersPerShaderStage());
        WGPULimits.maxStorageBuffersPerShaderStage(struct, maxStorageBuffersPerShaderStage());
        WGPULimits.maxStorageTexturesPerShaderStage(struct, maxStorageTexturesPerShaderStage());
        WGPULimits.maxVertexAttributes(struct, maxVertexAttributes());
        WGPULimits.maxVertexBuffers(struct, maxVertexBuffers());
        WGPULimits.maxBufferSize(struct, maxBufferSize());
        WGPULimits.maxVertexAttributes(struct, maxVertexAttributes());
        WGPULimits.maxVertexBufferArrayStride(struct, maxVertexBufferArrayStride());
        WGPULimits.maxInterStageShaderVariables(struct, maxInterStageShaderVariables());
        WGPULimits.maxColorAttachments(struct, maxColorAttachments());
        WGPULimits.maxColorAttachmentBytesPerSample(struct, maxColorAttachmentBytesPerSample());
        WGPULimits.maxComputeWorkgroupStorageSize(struct, maxComputeWorkgroupStorageSize());
        WGPULimits.maxComputeInvocationsPerWorkgroup(struct, maxComputeInvocationsPerWorkgroup());
        WGPULimits.maxComputeWorkgroupSizeX(struct, maxComputeWorkgroupSizeX());
        WGPULimits.maxComputeWorkgroupSizeY(struct, maxComputeWorkgroupSizeY());
        WGPULimits.maxComputeWorkgroupSizeZ(struct, maxComputeWorkgroupSizeZ());
        WGPULimits.maxComputeWorkgroupsPerDimension(struct, maxComputeWorkgroupsPerDimension());
    }
}
