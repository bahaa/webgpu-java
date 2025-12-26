package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUDepthStencilState;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface DepthStencilStateBlueprint extends StructBlueprint {

    TextureFormat format();

    OptionalBool depthWriteEnabled();

    CompareFunction depthCompare();

    Optional<StencilFaceState> stencilFront();

    Optional<StencilFaceState> stencilBack();

    @Option.DefaultInt(0xFFFFFFFF)
    int stencilReadMask();

    @Option.DefaultInt(0xFFFFFFFF)
    int stencilWriteMask();

    @Option.DefaultInt(0)
    int depthBias();

    @Option.DefaultInt(0)
    float depthBiasSlopeScale();

    @Option.DefaultInt(0)
    float depthBiasClamp();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUDepthStencilState.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUDepthStencilState.format(struct, this.format().value());
        WGPUDepthStencilState.depthWriteEnabled(struct, this.depthWriteEnabled().value());
        WGPUDepthStencilState.depthCompare(struct, this.depthCompare().value());
        stencilFront().ifPresent(stencilFront ->
                WGPUDepthStencilState.stencilFront(struct, stencilFront.toSegment(arena)));
        stencilBack().ifPresent(stencilBack ->
                WGPUDepthStencilState.stencilBack(struct, stencilBack.toSegment(arena)));
        WGPUDepthStencilState.stencilReadMask(struct, this.stencilReadMask());
        WGPUDepthStencilState.stencilWriteMask(struct, this.stencilWriteMask());
        WGPUDepthStencilState.depthBias(struct, this.depthBias());
        WGPUDepthStencilState.depthBiasSlopeScale(struct, this.depthBiasSlopeScale());
        WGPUDepthStencilState.depthBiasClamp(struct, this.depthBiasClamp());
    }
}
