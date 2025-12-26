package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.BindGroupLayout;
import io.github.bahaa.webgpu.ffm.WGPUBindGroupDescriptor;
import io.github.bahaa.webgpu.ffm.WGPUBindGroupEntry;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface BindGroupDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    BindGroupLayout layout();

    @Option.Singular("entry")
    List<BindGroupEntry> entries();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUBindGroupDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {

        label().ifPresent(label -> WGPUBindGroupDescriptor.label(struct, StringView.from(label).toSegment(arena)));

        WGPUBindGroupDescriptor.layout(struct, this.layout().pointer());

        WGPUBindGroupDescriptor.entries(struct,
                StructBlueprint.structArray(arena, WGPUBindGroupEntry.layout(), this.entries()));
        WGPUBindGroupDescriptor.entryCount(struct, this.entries().size());
    }
}
