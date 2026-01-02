package io.github.bahaa.webgpu.samples.math;

public class Vec2F {
    private final float[] data = new float[2];

    public Vec2F() {

    }

    public Vec2F(final float x, final float y) {
        this.data[0] = x;
        this.data[1] = y;
    }

    public float[] data() {
        return this.data;
    }

    public float x() {
        return this.data[0];
    }

    public float y() {
        return this.data[1];
    }
}
