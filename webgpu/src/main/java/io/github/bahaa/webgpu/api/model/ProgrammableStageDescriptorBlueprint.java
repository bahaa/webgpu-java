package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.ShaderModule;
import io.github.bahaa.webgpu.ffm.WGPUConstantEntry;
import io.github.bahaa.webgpu.ffm.WGPUProgrammableStageDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface ProgrammableStageDescriptorBlueprint extends StructBlueprint {

    ShaderModule module();

    Optional<String> entryPoint();

    List<ConstantEntry> constants();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUProgrammableStageDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUProgrammableStageDescriptor.module(struct, this.module().pointer());

        entryPoint().ifPresent(entryPoint ->
                WGPUProgrammableStageDescriptor.entryPoint(struct, StringView.from(entryPoint).toSegment(arena)));

        WGPUProgrammableStageDescriptor.constants(struct,
                StructBlueprint.structArray(arena, WGPUConstantEntry.layout(), this.constants()));
    }
}
