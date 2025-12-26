package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUConstantEntry;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface ConstantEntryBlueprint extends StructBlueprint {

    String key();

    double value();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUConstantEntry.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUConstantEntry.key(struct, StringView.from(this.key()).toSegment(arena));
        WGPUConstantEntry.value(struct, this.value());
    }
}
