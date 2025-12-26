package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUChainedStruct;
import io.github.bahaa.webgpu.ffm.WGPUSurfaceSourceMetalLayer;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.MemorySegment.NULL;

@Prototype.Blueprint
interface SurfaceSourceMetalLayerBlueprint extends SurfaceSource {

    MemorySegment layer();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUSurfaceSourceMetalLayer.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUSurfaceSourceMetalLayer.layer(struct, this.layer());

        final var chain = WGPUSurfaceSourceMetalLayer.chain(struct);

        WGPUChainedStruct.next(chain, NULL);
        WGPUChainedStruct.sType(chain, SType.SURFACE_SOURCE_METAL_LAYER.value());
    }
}
