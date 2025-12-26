package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUBlendState;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface BlendStateBlueprint extends StructBlueprint {

    BlendComponent color();

    BlendComponent alpha();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUBlendState.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUBlendState.color(struct, this.color().toSegment(arena));
        WGPUBlendState.alpha(struct, this.alpha().toSegment(arena));
    }
}
