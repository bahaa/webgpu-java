package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUBufferDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.EnumSet;
import java.util.Optional;

@Prototype.Blueprint
interface BufferDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    EnumSet<BufferUsage> usage();

    long size();

    boolean mappedAtCreation();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUBufferDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPUBufferDescriptor.label(struct, StringView.from(label).toSegment(arena)));

        WGPUBufferDescriptor.usage(struct, this.usage().stream()
                .mapToLong(BufferUsage::value)
                .reduce(0, (a, b) -> a | b));

        WGPUBufferDescriptor.size(struct, this.size());
        WGPUBufferDescriptor.mappedAtCreation(struct, this.mappedAtCreation() ? 1 : 0);
    }
}
