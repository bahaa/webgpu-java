package io.github.bahaa.webgpu.api.model;

public enum TextureSampleType {
    BINDING_NOT_USED(0X00000000),
    UNDEFINED(0X00000001),
    FLOAT(0X00000002),
    UNFILTERABLE_FLOAT(0X00000003),
    DEPTH(0X00000004),
    SINT(0X00000005),
    UINT(0X00000006),
    FORCE32(0X7FFFFFFF);

    private final int value;

    TextureSampleType(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

}
