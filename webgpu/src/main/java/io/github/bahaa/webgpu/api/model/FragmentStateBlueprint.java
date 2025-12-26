package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.ShaderModule;
import io.github.bahaa.webgpu.ffm.WGPUConstantEntry;
import io.github.bahaa.webgpu.ffm.WGPUFragmentState;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface FragmentStateBlueprint extends StructBlueprint {

    ShaderModule module();

    Optional<String> entryPoint();

    @Option.Singular("constant")
    List<ConstantEntry> constants();

    @Option.Singular("target")
    List<ColorTargetState> targets();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUFragmentState.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUFragmentState.module(struct, this.module().pointer());
        entryPoint().ifPresent(entryPoint ->
                WGPUFragmentState.entryPoint(struct, StringView.from(entryPoint).toSegment(arena)));

        WGPUFragmentState.constants(struct,
                StructBlueprint.structArray(arena, WGPUConstantEntry.layout(), this.constants()));
        WGPUFragmentState.constantCount(struct, this.constants().size());

        WGPUFragmentState.targets(struct,
                StructBlueprint.structArray(arena, WGPUConstantEntry.layout(), this.targets()));
        WGPUFragmentState.targetCount(struct, this.targets().size());
    }
}
