package io.github.bahaa.webgpu.api.model;


import io.github.bahaa.webgpu.ffm.WGPUTextureDescriptor;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface TextureDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    @Option.Singular("usage")
    EnumSet<TextureUsage> usage();

    @Option.Default("DIMENSION_2D")
    TextureDimension dimension();

    Extent3D size();

    TextureFormat format();

    @Option.DefaultInt(1)
    int mipLevelCount();

    @Option.DefaultInt(1)
    int sampleCount();

    @Option.Singular("viewFormat")
    List<TextureFormat> viewFormats();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUTextureDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPUTextureDescriptor.label(struct, StringView.from(label).toSegment(arena)));
        WGPUTextureDescriptor.usage(struct, this.usage().stream()
                .mapToLong(TextureUsage::value)
                .reduce(0, (a, b) -> a | b));
        WGPUTextureDescriptor.dimension(struct, this.dimension().value());
        WGPUTextureDescriptor.size(struct, this.size().toSegment(arena));
        WGPUTextureDescriptor.format(struct, this.format().value());
        WGPUTextureDescriptor.mipLevelCount(struct, this.mipLevelCount());
        WGPUTextureDescriptor.sampleCount(struct, this.sampleCount());

        final var segment = arena.allocate(ValueLayout.JAVA_INT, this.viewFormats().size());
        for (var i = 0; i < this.viewFormats().size(); i++) {
            segment.setAtIndex(ValueLayout.JAVA_INT, i, this.viewFormats().get(i).value());
        }
        WGPUTextureDescriptor.viewFormats(struct, segment);
    }
}
