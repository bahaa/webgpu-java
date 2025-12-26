package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUOrigin3D;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class Origin3D implements StructBlueprint {

    private final int x;
    private final int y;
    private final int z;

    private Origin3D(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Origin3D of(final int x, final int y, final int z) {
        return new Origin3D(x, y, z);
    }

    @Override
    public MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUOrigin3D.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    public void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUOrigin3D.x(struct, this.x);
        WGPUOrigin3D.y(struct, this.y);
        WGPUOrigin3D.z(struct, this.z);
    }
}
