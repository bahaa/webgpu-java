package io.github.bahaa.webgpu.api.model;


import io.github.bahaa.webgpu.ffm.WGPUStorageTextureBindingLayout;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface StorageTextureBindingLayoutBlueprint extends StructBlueprint {

    @Option.Default("WRITE_ONLY")
    StorageTextureAccess access();

    TextureFormat format();

    @Option.Default("D2")
    TextureViewDimension viewDimension();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUStorageTextureBindingLayout.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUStorageTextureBindingLayout.format(struct, this.format().value());
        WGPUStorageTextureBindingLayout.viewDimension(struct, this.viewDimension().value());
        WGPUStorageTextureBindingLayout.access(struct, this.access().value());
    }
}
