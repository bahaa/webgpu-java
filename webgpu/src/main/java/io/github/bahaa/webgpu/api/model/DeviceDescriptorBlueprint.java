package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUDeviceDescriptor;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface DeviceDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    @Option.Singular("requiredFeature")
    List<FeatureName> requiredFeatures();

    Optional<GPULimitsBlueprint> requiredLimits();

    Optional<QueueDescriptorBlueprint> defaultQueue();

    Optional<DeviceLostCallbackInfoBlueprint> deviceLostCallbackInfo();

    Optional<UncapturedErrorCallbackInfoBlueprint> uncapturedErrorCallbackInfo();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUDeviceDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPUDeviceDescriptor.label(struct, StringView.from(label).toSegment(arena)));

        final var features = arena.allocate(ValueLayout.JAVA_INT, requiredFeatures().size());
        for (var i = 0; i < requiredFeatures().size(); i++) {
            features.setAtIndex(ValueLayout.JAVA_INT, i, requiredFeatures().get(i).value());
        }
        WGPUDeviceDescriptor.requiredFeatures(struct, features);
        WGPUDeviceDescriptor.requiredFeatureCount(struct, requiredFeatures().size());

        requiredLimits().ifPresent(requiredLimits ->
                WGPUDeviceDescriptor.requiredLimits(struct, requiredLimits.toSegment(arena)));

        defaultQueue().ifPresent(defaultQueue ->
                WGPUDeviceDescriptor.defaultQueue(struct, defaultQueue.toSegment(arena)));

        deviceLostCallbackInfo().ifPresent(deviceLostCallbackInfo ->
                WGPUDeviceDescriptor.deviceLostCallbackInfo(struct, deviceLostCallbackInfo.toSegment(arena)));

        uncapturedErrorCallbackInfo().ifPresent(uncapturedErrorCallbackInfo ->
                WGPUDeviceDescriptor.uncapturedErrorCallbackInfo(struct, uncapturedErrorCallbackInfo.toSegment(arena)));
    }
}
