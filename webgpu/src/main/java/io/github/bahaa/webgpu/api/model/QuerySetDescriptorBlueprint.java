package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUQuerySetDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface QuerySetDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    QueryType type();

    int count();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUQuerySetDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPUQuerySetDescriptor.label(struct, StringView.from(label).toSegment(arena)));
        WGPUQuerySetDescriptor.type(struct, type().value());
        WGPUQuerySetDescriptor.count(struct, count());
    }
}
