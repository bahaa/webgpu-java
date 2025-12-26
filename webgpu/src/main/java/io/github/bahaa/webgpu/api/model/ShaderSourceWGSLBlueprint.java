package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUChainedStruct;
import io.github.bahaa.webgpu.ffm.WGPUShaderSourceWGSL;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface ShaderSourceWGSLBlueprint extends ShaderSource {

    String code();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUShaderSourceWGSL.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUShaderSourceWGSL.code(struct, StringView.from(code()).toSegment(arena));
        WGPUChainedStruct.sType(WGPUShaderSourceWGSL.chain(struct), SType.SHADER_SOURCE_WGSL.value());
    }
}
