package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUBlendComponent;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface BlendComponentBlueprint extends StructBlueprint {
    @Option.Default("ADD")
    BlendOperation operation();

    @Option.Default("ONE")
    BlendFactor srcFactor();

    @Option.Default("ZERO")
    BlendFactor dstFactor();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUBlendComponent.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUBlendComponent.operation(struct, operation().value());
        WGPUBlendComponent.dstFactor(struct, dstFactor().value());
        WGPUBlendComponent.srcFactor(struct, srcFactor().value());
    }
}
