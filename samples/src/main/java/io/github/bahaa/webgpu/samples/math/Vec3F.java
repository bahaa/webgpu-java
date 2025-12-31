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

    public static Vec3F add(final Vec3F v1, final Vec3F v2) {
        return new Vec3F(v1.data[0] + v2.data[0], v1.data[1] + v2.data[1], v1.data[2] + v2.data[2]);
    }

    public static Vec3F subtract(final Vec3F v1, final Vec3F v2) {
        return new Vec3F(v1.data[0] - v2.data[0], v1.data[1] - v2.data[1], v1.data[2] - v2.data[2]);
    }

    public static Vec3F cross(final Vec3F v1, final Vec3F v2) {
        final var t1 = v1.data[2] * v2.data[0] - v1.data[0] * v2.data[2];
        final var t2 = v1.data[0] * v2.data[1] - v1.data[1] * v2.data[0];
        return new Vec3F(v1.data[1] * v2.data[2] - v1.data[2] * v2.data[1], t1, t2);
    }

    public static float dot(final Vec3F v1, final Vec3F v2) {
        return v1.x() * v2.x() + v1.y() * v2.y() + v1.z() * v2.z();
    }

    public Vec3F normalize() {
        final var len = (float) Math.sqrt(x() * x() + y() * y() + z() * z());

        if (len > 0.000_01f) {
            return new Vec3F(x() / len, y() / len, z() / len);
        } else {
            return new Vec3F();
        }
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
