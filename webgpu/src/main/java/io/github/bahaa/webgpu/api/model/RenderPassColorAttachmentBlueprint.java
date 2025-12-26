package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.TextureView;
import io.github.bahaa.webgpu.ffm.WGPURenderPassColorAttachment;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface RenderPassColorAttachmentBlueprint extends StructBlueprint {


    TextureView view();

    @Option.DefaultInt((int) 4_294_967_295L)
    int depthSlice();

    Optional<TextureView> resolveTarget();

    LoadOp loadOp();

    StoreOp storeOp();

    Color clearValue();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPURenderPassColorAttachment.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPURenderPassColorAttachment.view(struct, this.view().pointer());
        WGPURenderPassColorAttachment.depthSlice(struct, this.depthSlice());
        resolveTarget().ifPresent(resolveTarget ->
                WGPURenderPassColorAttachment.resolveTarget(struct, resolveTarget.pointer()));
        WGPURenderPassColorAttachment.loadOp(struct, this.loadOp().value());
        WGPURenderPassColorAttachment.storeOp(struct, this.storeOp().value());
        WGPURenderPassColorAttachment.clearValue(struct, this.clearValue().toSegment(arena));
    }
}
