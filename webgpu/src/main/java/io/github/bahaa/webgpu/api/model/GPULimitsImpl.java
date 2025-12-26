package io.github.bahaa.webgpu.api.model;


public record GPULimitsImpl(int maxTextureDimension1D, int maxTextureDimension2D, int maxTextureDimension3D,
                            int maxTextureArrayLayers, int maxBindGroups, int maxBindGroupsPlusVertexBuffers,
                            int maxBindingsPerBindGroup, int maxDynamicUniformBuffersPerPipelineLayout,
                            int maxDynamicStorageBuffersPerPipelineLayout, int maxSampledTexturesPerShaderStage,
                            int maxSamplersPerShaderStage, int maxStorageBuffersPerShaderStage,
                            int maxStorageTexturesPerShaderStage, int maxUniformBuffersPerShaderStage,
                            long maxUniformBufferBindingSize, long maxStorageBufferBindingSize,
                            int minUniformBufferOffsetAlignment, int minStorageBufferOffsetAlignment,
                            int maxVertexBuffers,
                            long maxBufferSize, int maxVertexAttributes, int maxVertexBufferArrayStride,
                            int maxInterStageShaderVariables, int maxColorAttachments,
                            int maxColorAttachmentBytesPerSample,
                            int maxComputeWorkgroupStorageSize, int maxComputeInvocationsPerWorkgroup,
                            int maxComputeWorkgroupSizeX, int maxComputeWorkgroupSizeY, int maxComputeWorkgroupSizeZ,
                            int maxComputeWorkgroupsPerDimension) implements GPULimitsBlueprint {

}
