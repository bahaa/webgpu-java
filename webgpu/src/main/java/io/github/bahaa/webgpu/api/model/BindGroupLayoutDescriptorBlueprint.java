package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUBindGroupLayoutDescriptor;
import io.github.bahaa.webgpu.ffm.WGPUBindGroupLayoutEntry;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface BindGroupLayoutDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    @Option.Singular("entry")
    List<BindGroupLayoutEntry> entries();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUBindGroupLayoutDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {

        label().ifPresent(label ->
                WGPUBindGroupLayoutDescriptor.label(struct, StringView.from(label).toSegment(arena)));

        WGPUBindGroupLayoutDescriptor.entries(struct,
                StructBlueprint.structArray(arena, WGPUBindGroupLayoutEntry.layout(), this.entries()));
        WGPUBindGroupLayoutDescriptor.entryCount(struct, this.entries().size());
    }
}
