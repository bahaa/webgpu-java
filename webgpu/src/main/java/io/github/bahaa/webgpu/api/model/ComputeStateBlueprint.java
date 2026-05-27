package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.ShaderModule;
import io.github.bahaa.webgpu.ffm.WGPUComputeState;
import io.github.bahaa.webgpu.ffm.WGPUConstantEntry;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface ComputeStateBlueprint extends StructBlueprint {

    ShaderModule module();

    Optional<String> entryPoint();

    List<ConstantEntry> constants();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUComputeState.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUComputeState.module(struct, this.module().pointer());

        entryPoint().ifPresent(entryPoint ->
                WGPUComputeState.entryPoint(struct, StringView.from(entryPoint).toSegment(arena)));

        WGPUComputeState.constants(struct,
                StructBlueprint.structArray(arena, WGPUConstantEntry.layout(), this.constants()));
    }
}
