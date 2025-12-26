package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUInstanceDescriptor;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface InstanceDescriptorBlueprint extends StructBlueprint {

    /**
     * Instance features to enable
     */
    @Option.DefaultCode("InstanceCapabilities.builder().build()")
    Optional<InstanceCapabilities> features();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUInstanceDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        features().ifPresent(features -> WGPUInstanceDescriptor.features(struct, features.toSegment(arena)));
    }
}
