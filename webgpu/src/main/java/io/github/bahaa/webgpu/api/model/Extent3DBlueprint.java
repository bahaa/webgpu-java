package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUExtent3D;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface Extent3DBlueprint extends StructBlueprint {

    int width();

    @Option.DefaultInt(1)
    int height();

    @Option.DefaultInt(1)
    int depthOrArrayLayers();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUExtent3D.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUExtent3D.width(struct, this.width());
        WGPUExtent3D.height(struct, this.height());
        WGPUExtent3D.depthOrArrayLayers(struct, this.depthOrArrayLayers());
    }
}
