package io.github.bahaa.webgpu.api.model;


import io.github.bahaa.webgpu.ffm.WGPUBindGroupLayoutEntry;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.EnumSet;
import java.util.Optional;

@Prototype.Blueprint
interface BindGroupLayoutEntryBlueprint extends StructBlueprint {

    int binding();

    EnumSet<ShaderStage> visibility();

    BufferBindingLayout buffer();

    Optional<SamplerBindingLayout> sampler();

    Optional<TextureBindingLayout> texture();

    Optional<StorageTextureBindingLayout> storageTexture();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUBindGroupLayoutEntry.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUBindGroupLayoutEntry.binding(struct, this.binding());

        WGPUBindGroupLayoutEntry.visibility(struct, this.visibility().stream()
                .mapToLong(ShaderStage::value)
                .reduce(0, (a, b) -> a | b));

        WGPUBindGroupLayoutEntry.buffer(struct, this.buffer().toSegment(arena));
        sampler().ifPresent(sampler -> WGPUBindGroupLayoutEntry.sampler(struct, sampler.toSegment(arena)));
        texture().ifPresent(texture -> WGPUBindGroupLayoutEntry.texture(struct, texture.toSegment(arena)));
        storageTexture().ifPresent(storageTexture ->
                WGPUBindGroupLayoutEntry.storageTexture(struct, storageTexture.toSegment(arena)));
    }
}
