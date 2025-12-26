package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUComputePassDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface ComputePassDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    Optional<ComputePassTimestampWrites> timestampWrites();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUComputePassDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPUComputePassDescriptor.label(struct, StringView.from(label).toSegment(arena)));
        timestampWrites().ifPresent(timestampWrites ->
                WGPUComputePassDescriptor.timestampWrites(struct, timestampWrites.toSegmentAddress(arena)));
    }
}
