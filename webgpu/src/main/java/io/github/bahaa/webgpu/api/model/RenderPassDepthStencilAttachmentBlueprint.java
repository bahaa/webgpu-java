package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.TextureView;
import io.github.bahaa.webgpu.ffm.WGPURenderPassDepthStencilAttachment;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface RenderPassDepthStencilAttachmentBlueprint extends StructBlueprint {

    TextureView view();

    LoadOp depthLoadOp();

    StoreOp depthStoreOp();

    float depthClearValue();

    boolean depthReadOnly();

    Optional<LoadOp> stencilLoadOp();

    Optional<StoreOp> stencilStoreOp();

    int stencilClearValue();

    boolean stencilReadOnly();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPURenderPassDepthStencilAttachment.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPURenderPassDepthStencilAttachment.view(struct, this.view().pointer());
        WGPURenderPassDepthStencilAttachment.depthLoadOp(struct, this.depthLoadOp().value());
        WGPURenderPassDepthStencilAttachment.depthStoreOp(struct, this.depthStoreOp().value());
        WGPURenderPassDepthStencilAttachment.depthClearValue(struct, this.depthClearValue());
        WGPURenderPassDepthStencilAttachment.depthReadOnly(struct, this.depthReadOnly() ? 1 : 0);
        stencilLoadOp().ifPresent(stencilLoadOp ->
                WGPURenderPassDepthStencilAttachment.stencilLoadOp(struct, stencilLoadOp.value()));
        stencilStoreOp().ifPresent(stencilStoreOp ->
                WGPURenderPassDepthStencilAttachment.stencilStoreOp(struct, stencilStoreOp.value()));
        WGPURenderPassDepthStencilAttachment.stencilClearValue(struct, this.stencilClearValue());
        WGPURenderPassDepthStencilAttachment.stencilReadOnly(struct, this.stencilReadOnly() ? 1 : 0);
    }
}
