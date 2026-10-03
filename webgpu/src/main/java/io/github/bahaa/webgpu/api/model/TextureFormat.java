package io.github.bahaa.webgpu.api.model;

public enum TextureFormat {
    UNDEFINED(0x00000000),
    R8_UNORM(0x00000001),
    R8_SNORM(0x00000002),
    R8_UINT(0x00000003),
    R8_SINT(0x00000004),
    R16_UNORM(0x00000005),
    R16_SNORM(0x00000006),
    R16_UINT(0x00000007),
    R16_SINT(0x00000008),
    R16_FLOAT(0x00000009),
    RG8_UNORM(0x0000000A),
    RG8_SNORM(0x0000000B),
    RG8_UINT(0x0000000C),
    RG8_SINT(0x0000000D),
    R32_FLOAT(0x0000000E),
    R32_UINT(0x0000000F),
    R32_SINT(0x00000010),
    RG16_UNORM(0x00000011),
    RG16_SNORM(0x00000012),
    RG16_UINT(0x00000013),
    RG16_SINT(0x00000014),
    RG16_FLOAT(0x00000015),
    RGBA8_UNORM(0x00000016),
    RGBA8_UNORM_SRGB(0x00000017),
    RGBA8_SNORM(0x00000018),
    RGBA8_UINT(0x00000019),
    RGBA8_SINT(0x0000001A),
    BGRA8_UNORM(0x0000001B),
    BGRA8_UNORM_SRGB(0x0000001C),
    RGB10A2_UINT(0x0000001D),
    RGB10A2_UNORM(0x0000001E),
    RG11B10_UFLOAT(0x0000001F),
    RGB9E5_UFLOAT(0x00000020),
    RG32_FLOAT(0x00000021),
    RG32_UINT(0x00000022),
    RG32_SINT(0x00000023),
    RGBA16_UNORM(0x00000024),
    RGBA16_SNORM(0x00000025),
    RGBA16_UINT(0x00000026),
    RGBA16_SINT(0x00000027),
    RGBA16_FLOAT(0x00000028),
    RGBA32_FLOAT(0x00000029),
    RGBA32_UINT(0x0000002A),
    RGBA32_SINT(0x0000002B),
    STENCIL8(0x0000002C),
    DEPTH16_UNORM(0x0000002D),
    DEPTH24_PLUS(0x0000002E),
    DEPTH24_PLUS_STENCIL8(0x0000002F),
    DEPTH32_FLOAT(0x00000030),
    DEPTH32_FLOAT_STENCIL8(0x00000031),
    FORCE32(0x7FFFFFFF);

    private final int value;

    TextureFormat(final int value) {
        this.value = value;
    }

    public static TextureFormat fromValue(final int value) {
        for (final var format : values()) {
            if (format.value == value) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown TextureFormat value %d".formatted(value));
    }

    public int value() {
        return this.value;
    }
}

