package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUTexelCopyBufferLayout;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface TexelCopyBufferLayoutBlueprint extends StructBlueprint {

    long offset();

    int bytesPerRow();

    int rowsPerImage();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUTexelCopyBufferLayout.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUTexelCopyBufferLayout.offset(struct, offset());
        WGPUTexelCopyBufferLayout.bytesPerRow(struct, bytesPerRow());
        WGPUTexelCopyBufferLayout.rowsPerImage(struct, rowsPerImage());
    }
}
