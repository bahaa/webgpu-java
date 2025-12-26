package io.github.bahaa.webgpu.api.model;


import io.github.bahaa.webgpu.api.PipelineLayout;
import io.github.bahaa.webgpu.ffm.WGPUComputePipelineDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface ComputePipelineDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    PipelineLayout layout();

    ProgrammableStageDescriptor compute();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUComputePipelineDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label ->
                WGPUComputePipelineDescriptor.label(struct, StringView.from(label).toSegment(arena)));
        WGPUComputePipelineDescriptor.layout(struct, this.layout().pointer());
        WGPUComputePipelineDescriptor.compute(struct, this.compute().toSegment(arena));
    }
}
