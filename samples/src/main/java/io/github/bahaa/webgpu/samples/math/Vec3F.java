package io.github.bahaa.webgpu.samples.math;

public class Vec3F {

    private final float[] data = new float[3];

    public Vec3F() {
    }

    public Vec3F(final float x, final float y, final float z) {
        this.data[0] = x;
        this.data[1] = y;
        this.data[2] = z;
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

    public float z() {
        return this.data[2];
    }
}
