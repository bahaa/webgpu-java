package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPURenderBundleEncoderDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface RenderBundleEncoderDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    List<TextureFormat> colorFormats();

    Optional<TextureFormat> depthStencilFormat();

    int sampleCount();

    boolean depthReadOnly();

    boolean stencilReadOnly();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPURenderBundleEncoderDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label ->
                WGPURenderBundleEncoderDescriptor.label(struct, StringView.from(label).toSegment(arena)));

        final var colorFormats = arena.allocate(ValueLayout.JAVA_INT, colorFormats().size());
        for (var i = 0; i < colorFormats().size(); i++) {
            colorFormats.setAtIndex(ValueLayout.JAVA_INT, i, colorFormats().get(i).value());
        }
        WGPURenderBundleEncoderDescriptor.colorFormats(struct, colorFormats);
        WGPURenderBundleEncoderDescriptor.colorFormatCount(struct, colorFormats().size());

        WGPURenderBundleEncoderDescriptor.sampleCount(struct, sampleCount());
        WGPURenderBundleEncoderDescriptor.depthReadOnly(struct, depthReadOnly() ? 1 : 0);
        WGPURenderBundleEncoderDescriptor.stencilReadOnly(struct, stencilReadOnly() ? 1 : 0);
    }
}
