package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUColor;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class Color {

    private final double r;
    private final double g;
    private final double b;
    private final double a;

    private Color(final double r, final double g, final double b, final double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static Color rgba(final double r, final double g, final double b, final double a) {
        return new Color(r, g, b, a);
    }

    public static Color rgb(final double r, final double g, final double b) {
        return new Color(r, g, b, 1.0);
    }

    public float red() {
        return (float) this.r;
    }

    public float green() {
        return (float) this.g;
    }

    public float blue() {
        return (float) this.b;
    }

    public float alpha() {
        return (float) this.a;
    }


    public MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUColor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    public void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUColor.r(struct, this.r);
        WGPUColor.g(struct, this.g);
        WGPUColor.b(struct, this.b);
        WGPUColor.a(struct, this.a);
    }

    public MemorySegment toSegmentAddress(final Arena arena) {
        return MemorySegment.ofAddress(toSegment(arena).address());
    }
}
