package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUCommandEncoderDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface CommandEncoderDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUCommandEncoderDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label ->
                WGPUCommandEncoderDescriptor.label(struct, StringView.from(label).toSegment(arena)));
    }
}
