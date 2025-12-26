package io.github.bahaa.webgpu.api.model;


import io.github.bahaa.webgpu.ffm.WGPUPrimitiveState;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface PrimitiveStateBlueprint extends StructBlueprint {

    @Option.Default("TRIANGLE_LIST")
    PrimitiveTopology topology();

    @Option.Default("UNDEFINED")
    IndexFormat stripIndexFormat();

    @Option.Default("CCW")
    FrontFace frontFace();

    @Option.Default("NONE")
    CullMode cullMode();

    boolean unclippedDepth();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUPrimitiveState.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUPrimitiveState.topology(struct, this.topology().value());
        WGPUPrimitiveState.stripIndexFormat(struct, this.stripIndexFormat().value());
        WGPUPrimitiveState.frontFace(struct, this.frontFace().value());
        WGPUPrimitiveState.cullMode(struct, this.cullMode().value());
        WGPUPrimitiveState.unclippedDepth(struct, this.unclippedDepth() ? 1 : 0);
    }

}
