package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUSamplerBindingLayout;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface SamplerBindingLayoutBlueprint extends StructBlueprint {

    @Option.Default("FILTERING")
    SamplerBindingType type();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUSamplerBindingLayout.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUSamplerBindingLayout.type(struct, this.type().value());
    }
}
