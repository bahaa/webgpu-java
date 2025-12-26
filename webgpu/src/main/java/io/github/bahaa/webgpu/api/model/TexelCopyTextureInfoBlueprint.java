package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.Texture;
import io.github.bahaa.webgpu.ffm.WGPUTexelCopyTextureInfo;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface TexelCopyTextureInfoBlueprint extends StructBlueprint {

    Texture texture();

    int mipLevel();

    @Option.DefaultCode("Origin3D.of(0, 0, 0)")
    Origin3D origin();

    @Option.Default("ALL")
    TextureAspect aspect();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUTexelCopyTextureInfo.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUTexelCopyTextureInfo.texture(struct, texture().pointer());
        WGPUTexelCopyTextureInfo.mipLevel(struct, mipLevel());
        WGPUTexelCopyTextureInfo.origin(struct, origin().toSegment(arena));
        WGPUTexelCopyTextureInfo.aspect(struct, aspect().value());
    }
}
