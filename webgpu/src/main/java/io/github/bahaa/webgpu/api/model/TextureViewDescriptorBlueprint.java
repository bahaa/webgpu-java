package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUTextureViewDescriptor;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface TextureViewDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    Optional<TextureFormat> format();

    Optional<TextureViewDimension> dimension();

    int baseMipLevel();

    @Option.DefaultInt(1)
    int mipLevelCount();

    int baseArrayLayer();

    int arrayLayerCount();

    @Option.Default("ALL")
    TextureAspect aspect();

    @Option.Default("NONE")
    TextureUsage usage();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUTextureViewDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPUTextureViewDescriptor.label(struct, StringView.from(label).toSegment(arena)));
        format().ifPresent(format -> WGPUTextureViewDescriptor.format(struct, format.value()));
        dimension().ifPresent(dimension -> WGPUTextureViewDescriptor.dimension(struct, dimension.value()));
        WGPUTextureViewDescriptor.baseMipLevel(struct, this.baseMipLevel());
        WGPUTextureViewDescriptor.mipLevelCount(struct, this.mipLevelCount());
        WGPUTextureViewDescriptor.baseArrayLayer(struct, this.baseArrayLayer());
        WGPUTextureViewDescriptor.arrayLayerCount(struct, this.arrayLayerCount());
        WGPUTextureViewDescriptor.aspect(struct, this.aspect().value());
        WGPUTextureViewDescriptor.usage(struct, this.usage().value());
    }
}
