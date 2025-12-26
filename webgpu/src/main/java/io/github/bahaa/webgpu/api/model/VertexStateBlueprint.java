package io.github.bahaa.webgpu.api.model;


import io.github.bahaa.webgpu.api.ShaderModule;
import io.github.bahaa.webgpu.ffm.WGPUConstantEntry;
import io.github.bahaa.webgpu.ffm.WGPUVertexBufferLayout;
import io.github.bahaa.webgpu.ffm.WGPUVertexState;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface VertexStateBlueprint extends StructBlueprint {

    ShaderModule module();

    Optional<String> entryPoint();

    @Option.Singular("constant")
    List<ConstantEntry> constants();

    @Option.Singular("buffer")
    List<VertexBufferLayout> buffers();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUVertexState.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUVertexState.module(struct, this.module().pointer());
        entryPoint().ifPresent(entryPoint ->
                WGPUVertexState.entryPoint(struct, StringView.from(entryPoint).toSegment(arena)));

        WGPUVertexState.constants(struct,
                StructBlueprint.structArray(arena, WGPUConstantEntry.layout(), this.constants()));
        WGPUVertexState.constantCount(struct, this.constants().size());

        WGPUVertexState.buffers(struct,
                StructBlueprint.structArray(arena, WGPUVertexBufferLayout.layout(), this.buffers()));
        WGPUVertexState.bufferCount(struct, this.buffers().size());
    }
}
