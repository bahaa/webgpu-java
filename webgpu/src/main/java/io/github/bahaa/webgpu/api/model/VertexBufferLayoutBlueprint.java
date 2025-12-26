package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUVertexAttribute;
import io.github.bahaa.webgpu.ffm.WGPUVertexBufferLayout;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;

@Prototype.Blueprint
interface VertexBufferLayoutBlueprint extends StructBlueprint {

    @Option.Default("VERTEX")
    VertexStepMode stepMode();

    long arrayStride();

    @Option.Singular("attribute")
    List<VertexAttribute> attributes();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUVertexBufferLayout.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUVertexBufferLayout.stepMode(struct, this.stepMode().value());
        WGPUVertexBufferLayout.arrayStride(struct, this.arrayStride());

        WGPUVertexBufferLayout.attributes(struct,
                StructBlueprint.structArray(arena, WGPUVertexAttribute.layout(), this.attributes()));
        WGPUVertexBufferLayout.attributeCount(struct, this.attributes().size());
    }
}
