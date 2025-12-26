package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUVertexAttribute;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface VertexAttributeBlueprint extends StructBlueprint {

    VertexFormat format();

    long offset();

    int shaderLocation();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUVertexAttribute.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUVertexAttribute.format(struct, this.format().value());
        WGPUVertexAttribute.offset(struct, this.offset());
        WGPUVertexAttribute.shaderLocation(struct, this.shaderLocation());
    }
}
