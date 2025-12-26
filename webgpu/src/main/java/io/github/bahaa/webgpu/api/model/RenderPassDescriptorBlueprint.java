package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.QuerySet;
import io.github.bahaa.webgpu.ffm.WGPURenderPassColorAttachment;
import io.github.bahaa.webgpu.ffm.WGPURenderPassDescriptor;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface RenderPassDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    @Option.Singular
    List<RenderPassColorAttachment> colorAttachments();

    Optional<RenderPassDepthStencilAttachment> depthStencilAttachment();

    Optional<QuerySet> occlusionQuerySet();

    Optional<RenderPassTimestampWrites> timestampWrites();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPURenderPassDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPURenderPassDescriptor.label(struct, StringView.from(label).toSegment(arena)));

        WGPURenderPassDescriptor.colorAttachments(struct,
                StructBlueprint.structArray(arena, WGPURenderPassColorAttachment.layout(), this.colorAttachments()));
        WGPURenderPassDescriptor.colorAttachmentCount(struct, this.colorAttachments().size());
        depthStencilAttachment().ifPresent(depthStencilAttachment ->
                WGPURenderPassDescriptor.depthStencilAttachment(struct,
                        depthStencilAttachment.toSegmentAddress(arena)));
        occlusionQuerySet().ifPresent(occlusionQuerySet ->
                WGPURenderPassDescriptor.occlusionQuerySet(struct, occlusionQuerySet.pointer()));
        timestampWrites().ifPresent(timestampWrites ->
                WGPURenderPassDescriptor.timestampWrites(struct, timestampWrites.toSegmentAddress(arena)));
    }
}
