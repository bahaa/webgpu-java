package io.github.bahaa.webgpu.api.model;

public enum FeatureName {
    UNDEFINED(0X00000000),
    DEPTH_CLIP_CONTROL(0X00000001),
    DEPTH32_FLOAT_STENCIL8(0X00000002),
    TIMESTAMP_QUERY(0X00000003),
    TEXTURE_COMPRESSION_BC(0X00000004),
    TEXTURE_COMPRESSION_BC_SLICED_3D(0X00000005),
    TEXTURE_COMPRESSION_ETC2(0X00000006),
    TEXTURE_COMPRESSION_ASTC(0X00000007),
    TEXTURE_COMPRESSION_ASTC_SLICED_3D(0X00000008),
    INDIRECT_FIRST_INSTANCE(0X00000009),
    SHADER_F16(0X0000000A),
    RG11B10UFLOAT_RENDERABLE(0X0000000B),
    BGRA8UNORM_STORAGE(0X0000000C),
    FLOAT32_FILTERABLE(0X0000000D),
    FLOAT32_BLENDABLE(0X0000000E),
    CLIP_DISTANCES(0X0000000F),
    DUAL_SOURCE_BLENDING(0X00000010),
    Force32(0x7FFFFFFF),
    ;

    private final int value;

    FeatureName(final int value) {
        this.value = value;
    }

    public static FeatureName valueOf(final int value) {
        for (final var featureName : values()) {
            if (featureName.value() == value) {
                return featureName;
            }
        }
        return UNDEFINED;
    }

    public int value() {
        return this.value;
    }
}
