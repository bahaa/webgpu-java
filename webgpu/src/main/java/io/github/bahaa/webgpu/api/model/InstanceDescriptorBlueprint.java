package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUInstanceDescriptor;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.Set;

@Prototype.Blueprint
interface InstanceDescriptorBlueprint extends StructBlueprint {

    /**
     * Instance features to enable
     */
    @Option.Singular("requiredFeature")
    @Option.DefaultCode("java.util.EnumSet.noneOf(InstanceFeatureName.class)")
    Set<InstanceFeatureName> requiredFeatures();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUInstanceDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        final var segment = arena.allocate(ValueLayout.JAVA_INT, this.requiredFeatures().size());
        var index = 0;
        for (final var feature : this.requiredFeatures()) {
            segment.setAtIndex(ValueLayout.JAVA_INT, index, feature.value());
            index++;
        }
        WGPUInstanceDescriptor.requiredFeatures(struct, segment);
        WGPUInstanceDescriptor.requiredFeatureCount(struct, requiredFeatures().size());
    }
}
