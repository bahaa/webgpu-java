package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.QuerySet;
import io.github.bahaa.webgpu.ffm.WGPURenderPassTimestampWrites;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface RenderPassTimestampWritesBlueprint extends StructBlueprint {

    QuerySet querySet();

    int beginningOfPassWriteIndex();

    int endOfPassWriteIndex();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPURenderPassTimestampWrites.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPURenderPassTimestampWrites.querySet(struct, this.querySet().pointer());
        WGPURenderPassTimestampWrites.beginningOfPassWriteIndex(struct, this.beginningOfPassWriteIndex());
        WGPURenderPassTimestampWrites.endOfPassWriteIndex(struct, this.endOfPassWriteIndex());
    }
}
