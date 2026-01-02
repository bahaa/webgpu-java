package io.github.bahaa.webgpu.samples.math;

public class Vec4F {
    private final float[] data = new float[4];

    public Vec4F() {
    }

    public Vec4F(final float x, final float y, final float z, final float w) {
        this.data[0] = x;
        this.data[1] = y;
        this.data[2] = z;
        this.data[3] = w;
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

    public float w() {
        return this.data[3];
    }
}
