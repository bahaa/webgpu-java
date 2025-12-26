package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.Buffer;
import io.github.bahaa.webgpu.ffm.WGPUTexelCopyBufferInfo;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface TexelCopyBufferInfoBlueprint extends StructBlueprint {

    TexelCopyBufferLayout layout();

    Buffer buffer();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUTexelCopyBufferInfo.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUTexelCopyBufferInfo.layout(struct, layout().toSegment(arena));
        WGPUTexelCopyBufferInfo.buffer(struct, buffer().pointer());
    }
}
