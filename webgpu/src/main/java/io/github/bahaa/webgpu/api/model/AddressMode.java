package io.github.bahaa.webgpu.api.model;

public enum AddressMode {
    UNDEFINED(0X00000000),
    CLAMP_TO_EDGE(0X00000001),
    REPEAT(0X00000002),
    MIRROR_REPEAT(0X00000003),
    FORCE32(0X7FFFFFFF),
    ;

    private final int value;

    AddressMode(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
