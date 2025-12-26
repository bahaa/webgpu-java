package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUInstanceCapabilities;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface InstanceCapabilitiesBlueprint extends StructBlueprint {

    boolean timedWaitAnyEnable();

    int timedWaitAnyMaxCount();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUInstanceCapabilities.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUInstanceCapabilities.timedWaitAnyEnable(struct, timedWaitAnyEnable() ? 1 : 0);
        WGPUInstanceCapabilities.timedWaitAnyMaxCount(struct, timedWaitAnyMaxCount());
    }
}
