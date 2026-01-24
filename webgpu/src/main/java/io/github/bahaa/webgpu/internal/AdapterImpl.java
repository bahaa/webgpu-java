package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Adapter;
import io.github.bahaa.webgpu.api.Device;
import io.github.bahaa.webgpu.api.WebGpuException;
import io.github.bahaa.webgpu.api.model.*;
import io.github.bahaa.webgpu.ffm.*;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class AdapterImpl extends NativeObjectImpl implements Adapter {

    private final InstanceImpl instance;

    private final MemorySegment uncapturedErrorCallbackInfo;
    private final Arena arena = Arena.ofConfined();

    protected AdapterImpl(final MemorySegment pointer, final InstanceImpl instance) {
        super(pointer);
        this.instance = instance;

        final WGPUUncapturedErrorCallback.Function uncapturedErrorCallback =
                (device, type, message, userdata1, userdata2) -> {
                    throw new WebGpuException(StringView.from(message).value());
                };
        final var uncapturedErrorCallbackStub =
                WGPUUncapturedErrorCallback.allocate(uncapturedErrorCallback, this.arena);
        this.uncapturedErrorCallbackInfo = WGPUUncapturedErrorCallbackInfo.allocate(this.arena);
        WGPUUncapturedErrorCallbackInfo.callback(this.uncapturedErrorCallbackInfo, uncapturedErrorCallbackStub);
    }

    public static AdapterImpl from(final MemorySegment pointer, final InstanceImpl instance) {
        return new AdapterImpl(pointer, instance);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer(), this.arena);
    }

    @Override
    public CompletableFuture<Device> requestDevice(final DeviceDescriptor descriptor) {
        Objects.requireNonNull(descriptor, "descriptor is null");

        final var future = new CompletableFuture<Device>();

        final WGPURequestDeviceCallback.Function callback = (status, device, message,
                                                             userdata1, userdata2) -> {
            if (status == WGPURequestDeviceStatus_Success()) {
                future.complete(DeviceImpl.from(device, this));
            } else {
                future.completeExceptionally(new RuntimeException("WGPURequestDeviceStatus: %d"
                        .formatted(status)));
            }
        };
        try (final var arena = Arena.ofConfined()) {
            final var callbackStub = WGPURequestDeviceCallback.allocate(callback, arena);

            final var callbackInfo = WGPURequestDeviceCallbackInfo.allocate(arena);
            WGPURequestDeviceCallbackInfo.callback(callbackInfo, callbackStub);

            final var struct = descriptor.toSegment(arena);
            WGPUDeviceDescriptor.uncapturedErrorCallbackInfo(struct, this.uncapturedErrorCallbackInfo);

            var _ = wgpuAdapterRequestDevice(arena, this.pointer(),
                    struct, callbackInfo);
        }


        return future;
    }

    @Override
    public AdapterInfo info() {
        try (final var arena = Arena.ofConfined()) {
            final var infoStruct = WGPUAdapterInfo.allocate(arena);
            wgpuAdapterGetInfo(pointer(), infoStruct);
            final var info = new AdapterInfo(
                    StringView.from(WGPUAdapterInfo.vendor(infoStruct)).value(),
                    StringView.from(WGPUAdapterInfo.architecture(infoStruct)).value(),
                    StringView.from(WGPUAdapterInfo.device(infoStruct)).value(),
                    StringView.from(WGPUAdapterInfo.description(infoStruct)).value(),
                    BackendType.fromValue(WGPUAdapterInfo.backendType(infoStruct)),
                    AdapterType.fromValue(WGPUAdapterInfo.adapterType(infoStruct)),
                    WGPUAdapterInfo.vendorID(infoStruct),
                    WGPUAdapterInfo.deviceID(infoStruct)
            );
            wgpuAdapterInfoFreeMembers(infoStruct);
            return info;
        }
    }

    @Override
    public Set<FeatureName> features() {
        try (final var arena = Arena.ofConfined()) {
            final var struct = WGPUSupportedFeatures.allocate(arena);
            wgpuAdapterGetFeatures(this.pointer(), struct);

            final var features = EnumSet.noneOf(FeatureName.class);
            for (var i = 0; i < WGPUSupportedFeatures.featureCount(struct); i++) {
                final var feature =
                        FeatureName.valueOf(WGPUSupportedFeatures.features(struct).getAtIndex(ValueLayout.JAVA_INT, i));
                features.add(feature);
            }

            wgpuSupportedFeaturesFreeMembers(struct);
            return features;
        }
    }

    @Override
    public SupportedLimits limits() {
        try (final var arena = Arena.ofConfined()) {
            final var struct = WGPULimits.allocate(arena);
            wgpuAdapterGetLimits(this.pointer(), struct);
            return new SupportedLimits(
                    WGPULimits.maxTextureDimension1D(struct),
                    WGPULimits.maxTextureDimension2D(struct),
                    WGPULimits.maxTextureDimension3D(struct),
                    WGPULimits.maxTextureArrayLayers(struct),
                    WGPULimits.maxBindGroups(struct),
                    WGPULimits.maxBindGroupsPlusVertexBuffers(struct),
                    WGPULimits.maxBindingsPerBindGroup(struct),
                    WGPULimits.maxDynamicUniformBuffersPerPipelineLayout(struct),
                    WGPULimits.maxDynamicStorageBuffersPerPipelineLayout(struct),
                    WGPULimits.maxSampledTexturesPerShaderStage(struct),
                    WGPULimits.maxSamplersPerShaderStage(struct),
                    WGPULimits.maxStorageBuffersPerShaderStage(struct),
                    WGPULimits.maxStorageTexturesPerShaderStage(struct),
                    WGPULimits.maxUniformBuffersPerShaderStage(struct),
                    WGPULimits.maxUniformBufferBindingSize(struct),
                    WGPULimits.maxStorageBufferBindingSize(struct),
                    WGPULimits.minUniformBufferOffsetAlignment(struct),
                    WGPULimits.minStorageBufferOffsetAlignment(struct),
                    WGPULimits.maxVertexBuffers(struct),
                    WGPULimits.maxBufferSize(struct),
                    WGPULimits.maxVertexAttributes(struct),
                    WGPULimits.maxVertexBufferArrayStride(struct),
                    WGPULimits.maxInterStageShaderVariables(struct),
                    WGPULimits.maxColorAttachments(struct),
                    WGPULimits.maxColorAttachmentBytesPerSample(struct),
                    WGPULimits.maxComputeWorkgroupStorageSize(struct),
                    WGPULimits.maxComputeInvocationsPerWorkgroup(struct),
                    WGPULimits.maxComputeWorkgroupSizeX(struct),
                    WGPULimits.maxComputeWorkgroupSizeY(struct),
                    WGPULimits.maxComputeWorkgroupSizeZ(struct),
                    WGPULimits.maxComputeWorkgroupsPerDimension(struct)
            );
        }
    }

    @Override
    public boolean hasFeature(final FeatureName feature) {
        Objects.requireNonNull(feature, "feature is null");
        return wgpuAdapterHasFeature(pointer(), feature.value()) > 0;
    }

    InstanceImpl instance() {
        return this.instance;
    }

    private static class Cleaner extends ObjectCleaner {

        private final Arena arena;

        protected Cleaner(final MemorySegment pointer, final Arena arena) {
            super(pointer);
            this.arena = arena;
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuAdapterRelease(pointer);
            if (this.arena != null) {
                this.arena.close();
            }
        }
    }
}
