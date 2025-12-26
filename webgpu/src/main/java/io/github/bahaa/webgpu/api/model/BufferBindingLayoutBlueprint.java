package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUBufferBindingLayout;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface BufferBindingLayoutBlueprint extends StructBlueprint {

    @Option.Default("UNIFORM")
    BufferBindingType type();

    boolean hasDynamicOffset();

    long minBindingSize();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUBufferBindingLayout.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUBufferBindingLayout.type(struct, this.type().value());
        WGPUBufferBindingLayout.hasDynamicOffset(struct, this.hasDynamicOffset() ? 1 : 0);
        WGPUBufferBindingLayout.minBindingSize(struct, this.minBindingSize());
    }
}
