package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.QuerySet;
import io.github.bahaa.webgpu.ffm.WGPUPassTimestampWrites;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface PassTimestampWritesBlueprint extends StructBlueprint {
    QuerySet querySet();

    int beginningOfPassWriteIndex();

    int endOfPassWriteIndex();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUPassTimestampWrites.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUPassTimestampWrites.querySet(struct, this.querySet().pointer());
        WGPUPassTimestampWrites.beginningOfPassWriteIndex(struct, this.beginningOfPassWriteIndex());
        WGPUPassTimestampWrites.endOfPassWriteIndex(struct, this.endOfPassWriteIndex());
    }
}
