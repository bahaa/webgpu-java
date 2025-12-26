package io.github.bahaa.webgpu.api.model;

public enum VertexFormat {
    UINT8(0x00000001),
    UINT8X2(0x00000002),
    UINT8X4(0x00000003),
    SINT8(0x00000004),
    SINT8X2(0x00000005),
    SINT8X4(0x00000006),
    UNORM8(0x00000007),
    UNORM8X2(0x00000008),
    UNORM8X4(0x00000009),
    SNORM8(0x0000000A),
    SNORM8X2(0x0000000B),
    SNORM8X4(0x0000000C),
    UINT16(0x0000000D),
    UINT16X2(0x0000000E),
    UINT16X4(0x0000000F),
    SINT16(0x00000010),
    SINT16X2(0x00000011),
    SINT16X4(0x00000012),
    UNORM16(0x00000013),
    UNORM16X2(0x00000014),
    UNORM16X4(0x00000015),
    SNORM16(0x00000016),
    SNORM16X2(0x00000017),
    SNORM16X4(0x00000018),
    FLOAT16(0x00000019),
    FLOAT16X2(0x0000001A),
    FLOAT16X4(0x0000001B),
    FLOAT32(0x0000001C),
    FLOAT32X2(0x0000001D),
    FLOAT32X3(0x0000001E),
    FLOAT32X4(0x0000001F),
    UINT32(0x00000020),
    UINT32X2(0x00000021),
    UINT32X3(0x00000022),
    UINT32X4(0x00000023),
    SINT32(0x00000024),
    SINT32X2(0x00000025),
    SINT32X3(0x00000026),
    SINT32X4(0x00000027),
    UNORM10_10_10_2(0x00000028),
    UNORM8X4_BGRA(0x00000029),
    FORCE32(0x7FFFFFFF);

    private final int value;

    VertexFormat(final int value) {
        this.value = value;
    }

    public static VertexFormat fromValue(final int value) {
        for (final var format : values()) {
            if (format.value == value) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown WgpuVertexFormat value: " + value);
    }

    public int value() {
        return this.value;
    }
}
