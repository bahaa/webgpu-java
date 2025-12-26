package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUChainedStruct;
import io.github.bahaa.webgpu.ffm.WGPUShaderSourceSPIRV;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface ShaderSourceSPIRVBlueprint extends ShaderSource {

    byte[] code();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUShaderSourceSPIRV.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUShaderSourceSPIRV.code(struct, MemorySegment.ofAddress(MemorySegment.ofArray(this.code()).address()));
        WGPUShaderSourceSPIRV.codeSize(struct, this.code().length);
        WGPUChainedStruct.sType(WGPUShaderSourceSPIRV.chain(struct), SType.SHADER_SOURCE_SPIRV.value());
    }
}
