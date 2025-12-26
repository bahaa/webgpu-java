package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.BindGroupLayout;
import io.github.bahaa.webgpu.ffm.WGPUPipelineLayoutDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface PipelineLayoutDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    List<BindGroupLayout> bindGroupLayouts();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUPipelineLayoutDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label ->
                WGPUPipelineLayoutDescriptor.label(struct, StringView.from(label).toSegment(arena)));

        final var layouts = arena.allocate(ValueLayout.ADDRESS, bindGroupLayouts().size());

        for (var i = 0; i < bindGroupLayouts().size(); i++) {
            layouts.setAtIndex(ValueLayout.ADDRESS, i, bindGroupLayouts().get(i).pointer());
        }

        WGPUPipelineLayoutDescriptor.bindGroupLayouts(struct, layouts);
        WGPUPipelineLayoutDescriptor.bindGroupLayoutCount(struct, bindGroupLayouts().size());
    }
}
