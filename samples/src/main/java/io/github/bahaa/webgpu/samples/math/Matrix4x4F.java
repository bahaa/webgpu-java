package io.github.bahaa.webgpu.samples.math;

import java.util.Arrays;
import java.util.Objects;

public class Matrix4x4F {

    private final float[] data = new float[16];

    public Matrix4x4F() {
    }

    public Matrix4x4F(
            final float v00, final float v01, final float v02, final float v03,
            final float v10, final float v11, final float v12, final float v13,
            final float v20, final float v21, final float v22, final float v23,
            final float v30, final float v31, final float v32, final float v33
    ) {
        this.data[0] = v00;
        this.data[1] = v01;
        this.data[2] = v02;
        this.data[3] = v03;

        this.data[4] = v10;
        this.data[5] = v11;
        this.data[6] = v12;
        this.data[7] = v13;

        this.data[8] = v20;
        this.data[9] = v21;
        this.data[10] = v22;
        this.data[11] = v23;

        this.data[12] = v30;
        this.data[13] = v31;
        this.data[14] = v32;
        this.data[15] = v33;
    }

    public static Matrix4x4F identity() {
        return new Matrix4x4F(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
    }

    public static Matrix4x4F perspective(final float fov, final float aspect, final float zNear, final float zFar) {
        final var f = (float) Math.tan(Math.PI * 0.5 - 0.5 * fov);
        final var rangeInv = 1 / (zNear - zFar);

        return new Matrix4x4F(
                f / aspect, 0, 0, 0,
                0, f, 0, 0,
                0, 0, zFar * rangeInv, -1,
                0, 0, zFar * zNear * rangeInv, 0
        );
    }

    public float[] data() {
        return this.data;
    }

    public void translate(final float v0, final float v1, final float v2) {
        final var m00 = this.data[0];
        final var m01 = this.data[1];
        final var m02 = this.data[2];
        final var m03 = this.data[3];
        final var m10 = this.data[4];
        final var m11 = this.data[4 + 1];
        final var m12 = this.data[4 + 2];
        final var m13 = this.data[4 + 3];
        final var m20 = this.data[2 * 4];
        final var m21 = this.data[2 * 4 + 1];
        final var m22 = this.data[2 * 4 + 2];
        final var m23 = this.data[2 * 4 + 3];
        final var m30 = this.data[3 * 4];
        final var m31 = this.data[3 * 4 + 1];
        final var m32 = this.data[3 * 4 + 2];
        final var m33 = this.data[3 * 4 + 3];

        this.data[0] = m00;
        this.data[1] = m01;
        this.data[2] = m02;
        this.data[3] = m03;
        this.data[4] = m10;
        this.data[5] = m11;
        this.data[6] = m12;
        this.data[7] = m13;
        this.data[8] = m20;
        this.data[9] = m21;
        this.data[10] = m22;
        this.data[11] = m23;
        this.data[12] = m00 * v0 + m10 * v1 + m20 * v2 + m30;
        this.data[13] = m01 * v0 + m11 * v1 + m21 * v2 + m31;
        this.data[14] = m02 * v0 + m12 * v1 + m22 * v2 + m32;
        this.data[15] = m03 * v0 + m13 * v1 + m23 * v2 + m33;
    }

    public void rotate(final Vec3F axis, final float angle) {
        var x = axis.x();
        var y = axis.y();
        var z = axis.z();
        final var n = Math.sqrt(x * x + y * y + z * z);
        x /= (float) n;
        y /= (float) n;
        z /= (float) n;
        final var xx = x * x;
        final var yy = y * y;
        final var zz = z * z;
        final var c = (float) Math.cos(angle);
        final var s = (float) Math.sin(angle);
        final var oneMinusCosine = 1 - c;

        final var r00 = xx + (1 - xx) * c;
        final var r01 = x * y * oneMinusCosine + z * s;
        final var r02 = x * z * oneMinusCosine - y * s;
        final var r10 = x * y * oneMinusCosine - z * s;
        final var r11 = yy + (1 - yy) * c;
        final var r12 = y * z * oneMinusCosine + x * s;
        final var r20 = x * z * oneMinusCosine + y * s;
        final var r21 = y * z * oneMinusCosine - x * s;
        final var r22 = zz + (1 - zz) * c;

        final var m00 = this.data[0];
        final var m01 = this.data[1];
        final var m02 = this.data[2];
        final var m03 = this.data[3];
        final var m10 = this.data[4];
        final var m11 = this.data[5];
        final var m12 = this.data[6];
        final var m13 = this.data[7];
        final var m20 = this.data[8];
        final var m21 = this.data[9];
        final var m22 = this.data[10];
        final var m23 = this.data[11];

        this.data[0] = r00 * m00 + r01 * m10 + r02 * m20;
        this.data[1] = r00 * m01 + r01 * m11 + r02 * m21;
        this.data[2] = r00 * m02 + r01 * m12 + r02 * m22;
        this.data[3] = r00 * m03 + r01 * m13 + r02 * m23;
        this.data[4] = r10 * m00 + r11 * m10 + r12 * m20;
        this.data[5] = r10 * m01 + r11 * m11 + r12 * m21;
        this.data[6] = r10 * m02 + r11 * m12 + r12 * m22;
        this.data[7] = r10 * m03 + r11 * m13 + r12 * m23;
        this.data[8] = r20 * m00 + r21 * m10 + r22 * m20;
        this.data[9] = r20 * m01 + r21 * m11 + r22 * m21;
        this.data[10] = r20 * m02 + r21 * m12 + r22 * m22;
        this.data[11] = r20 * m03 + r21 * m13 + r22 * m23;
    }

    public Matrix4x4F multiply(final Matrix4x4F other) {
        final var a00 = this.data[0];
        final var a01 = this.data[1];
        final var a02 = this.data[2];
        final var a03 = this.data[3];
        final var a10 = this.data[4];
        final var a11 = this.data[4 + 1];
        final var a12 = this.data[4 + 2];
        final var a13 = this.data[4 + 3];
        final var a20 = this.data[8];
        final var a21 = this.data[8 + 1];
        final var a22 = this.data[8 + 2];
        final var a23 = this.data[8 + 3];
        final var a30 = this.data[12];
        final var a31 = this.data[12 + 1];
        final var a32 = this.data[12 + 2];
        final var a33 = this.data[12 + 3];

        final var b00 = other.data[0];
        final var b01 = other.data[1];
        final var b02 = other.data[2];
        final var b03 = other.data[3];
        final var b10 = other.data[4];
        final var b11 = other.data[4 + 1];
        final var b12 = other.data[4 + 2];
        final var b13 = other.data[4 + 3];
        final var b20 = other.data[8];
        final var b21 = other.data[8 + 1];
        final var b22 = other.data[8 + 2];
        final var b23 = other.data[8 + 3];
        final var b30 = other.data[12];
        final var b31 = other.data[12 + 1];
        final var b32 = other.data[12 + 2];
        final var b33 = other.data[12 + 3];

        return new Matrix4x4F(
                a00 * b00 + a10 * b01 + a20 * b02 + a30 * b03,
                a01 * b00 + a11 * b01 + a21 * b02 + a31 * b03,
                a02 * b00 + a12 * b01 + a22 * b02 + a32 * b03,
                a03 * b00 + a13 * b01 + a23 * b02 + a33 * b03,
                a00 * b10 + a10 * b11 + a20 * b12 + a30 * b13,
                a01 * b10 + a11 * b11 + a21 * b12 + a31 * b13,
                a02 * b10 + a12 * b11 + a22 * b12 + a32 * b13,
                a03 * b10 + a13 * b11 + a23 * b12 + a33 * b13,
                a00 * b20 + a10 * b21 + a20 * b22 + a30 * b23,
                a01 * b20 + a11 * b21 + a21 * b22 + a31 * b23,
                a02 * b20 + a12 * b21 + a22 * b22 + a32 * b23,
                a03 * b20 + a13 * b21 + a23 * b22 + a33 * b23,
                a00 * b30 + a10 * b31 + a20 * b32 + a30 * b33,
                a01 * b30 + a11 * b31 + a21 * b32 + a31 * b33,
                a02 * b30 + a12 * b31 + a22 * b32 + a32 * b33,
                a03 * b30 + a13 * b31 + a23 * b32 + a33 * b33
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Matrix4x4F that)) {
            return false;
        }
        return Objects.deepEquals(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data);
    }
}
