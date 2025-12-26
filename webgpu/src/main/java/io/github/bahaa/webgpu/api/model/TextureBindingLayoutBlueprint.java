package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUTextureBindingLayout;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface TextureBindingLayoutBlueprint extends StructBlueprint {

    @Option.Default("D2")
    TextureViewDimension viewDimension();

    boolean multisampled();

    @Option.Default("FLOAT")
    TextureSampleType sampleType();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUTextureBindingLayout.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUTextureBindingLayout.viewDimension(struct, this.viewDimension().value());
        WGPUTextureBindingLayout.multisampled(struct, this.multisampled() ? 1 : 0);
        WGPUTextureBindingLayout.sampleType(struct, this.sampleType().value());
    }
}
