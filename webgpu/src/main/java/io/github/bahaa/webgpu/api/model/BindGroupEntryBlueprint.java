package io.github.bahaa.webgpu.api.model;


import io.github.bahaa.webgpu.api.Buffer;
import io.github.bahaa.webgpu.api.Sampler;
import io.github.bahaa.webgpu.api.TextureView;
import io.github.bahaa.webgpu.ffm.WGPUBindGroupEntry;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface BindGroupEntryBlueprint extends StructBlueprint {

    int binding();

    Optional<Buffer> buffer();

    long offset();

    long size();

    Optional<Sampler> sampler();

    Optional<TextureView> textureView();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUBindGroupEntry.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUBindGroupEntry.binding(struct, this.binding());
        buffer().ifPresent(buffer -> WGPUBindGroupEntry.buffer(struct, buffer.pointer()));
        WGPUBindGroupEntry.offset(struct, this.offset());
        WGPUBindGroupEntry.size(struct, this.size());
        sampler().ifPresent(sampler -> WGPUBindGroupEntry.sampler(struct, sampler.pointer()));
        textureView().ifPresent(textureView -> WGPUBindGroupEntry.textureView(struct, textureView.pointer()));
    }
}
