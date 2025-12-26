package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.QuerySet;
import io.github.bahaa.webgpu.ffm.WGPUComputePassTimestampWrites;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface ComputePassTimestampWritesBlueprint extends StructBlueprint {
    QuerySet querySet();

    int beginningOfPassWriteIndex();

    int endOfPassWriteIndex();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUComputePassTimestampWrites.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUComputePassTimestampWrites.querySet(struct, this.querySet().pointer());
        WGPUComputePassTimestampWrites.beginningOfPassWriteIndex(struct, this.beginningOfPassWriteIndex());
        WGPUComputePassTimestampWrites.endOfPassWriteIndex(struct, this.endOfPassWriteIndex());
    }
}
