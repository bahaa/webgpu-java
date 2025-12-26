package io.github.bahaa.webgpu.api;


import io.github.bahaa.webgpu.api.model.Extent3D;
import io.github.bahaa.webgpu.api.model.TexelCopyBufferLayout;
import io.github.bahaa.webgpu.api.model.TexelCopyTextureInfo;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;

public interface Queue extends ObjectBase {

    void submit(final List<CommandBuffer> commands);

    void writeBuffer(final Buffer buffer, final long bufferOffset, final MemorySegment data, final long size);

    default void writeBuffer(final Buffer buffer, final long bufferOffset, final float[] data) {
        try (final var arena = Arena.ofConfined()) {
            final var segment = MemorySegment.ofArray(data);
            final var nativeSegment = arena.allocate(segment.byteSize());
            nativeSegment.copyFrom(segment);
            writeBuffer(buffer, bufferOffset, nativeSegment, nativeSegment.byteSize());
        }
    }

    default void writeBuffer(final Buffer buffer, final long bufferOffset, final int[] data) {
        try (final var arena = Arena.ofConfined()) {
            final var segment = MemorySegment.ofArray(data);
            final var nativeSegment = arena.allocate(segment.byteSize());
            nativeSegment.copyFrom(segment);
            writeBuffer(buffer, bufferOffset, nativeSegment, nativeSegment.byteSize());
        }
    }

    void writeTexture(final TexelCopyTextureInfo destination, final MemorySegment data, final long dataSize,
                      final TexelCopyBufferLayout dataLayout, final Extent3D writeSize);
}
