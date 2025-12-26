package io.github.bahaa.webgpu.api.model;


import io.github.bahaa.webgpu.api.PipelineLayout;
import io.github.bahaa.webgpu.ffm.WGPURenderPipelineDescriptor;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface RenderPipelineDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    Optional<PipelineLayout> layout();

    VertexState vertex();

    @Option.DefaultCode("PrimitiveState.create()")
    Optional<PrimitiveState> primitive();

    Optional<DepthStencilState> depthStencil();

    @Option.DefaultCode("MultisampleState.create()")
    MultisampleState multisample();

    FragmentState fragment();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPURenderPipelineDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label ->
                WGPURenderPipelineDescriptor.label(struct, StringView.from(label).toSegment(arena)));
        layout().ifPresent(layout ->
                WGPURenderPipelineDescriptor.layout(struct, layout.pointer()));
        WGPURenderPipelineDescriptor.vertex(struct, this.vertex().toSegment(arena));
        primitive().ifPresent(primitive -> WGPURenderPipelineDescriptor.primitive(struct, primitive.toSegment(arena)));
        depthStencil().ifPresent(depthStencil ->
                WGPURenderPipelineDescriptor.depthStencil(struct, depthStencil.toSegmentAddress(arena)));
        WGPURenderPipelineDescriptor.multisample(struct, this.multisample().toSegment(arena));
        WGPURenderPipelineDescriptor.fragment(struct, this.fragment().toSegmentAddress(arena));
    }
}
