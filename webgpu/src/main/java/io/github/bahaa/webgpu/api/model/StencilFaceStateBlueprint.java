package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUStencilFaceState;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface StencilFaceStateBlueprint extends StructBlueprint {

    @Option.Default("ALWAYS")
    CompareFunction compare();

    @Option.Default("KEEP")
    StencilOperation failOp();

    @Option.Default("KEEP")
    StencilOperation depthFailOp();

    @Option.Default("KEEP")
    StencilOperation passOp();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUStencilFaceState.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUStencilFaceState.compare(struct, this.compare().value());
        WGPUStencilFaceState.failOp(struct, this.failOp().value());
        WGPUStencilFaceState.depthFailOp(struct, this.depthFailOp().value());
        WGPUStencilFaceState.passOp(struct, this.passOp().value());
    }
}
