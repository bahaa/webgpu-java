package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUMultisampleState;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface MultisampleStateBlueprint extends StructBlueprint {

    @Option.DefaultInt(1)
    int count();

    @Option.DefaultInt(0xFFFFFFFF)
    int mask();

    @Option.DefaultBoolean(false)
    boolean alphaToCoverageEnabled();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUMultisampleState.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUMultisampleState.count(struct, this.count());
        WGPUMultisampleState.mask(struct, this.mask());
        WGPUMultisampleState.alphaToCoverageEnabled(struct, this.alphaToCoverageEnabled() ? 1 : 0);
    }
}
