package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUCommandBufferDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface CommandBufferDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUCommandBufferDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPUCommandBufferDescriptor.label(struct, StringView.from(label).toSegment(arena)));
    }
}
