package io.github.bahaa.webgpu.api.model;

public enum FilterMode {
    UNDEFINED(0X00000000),
    NEAREST(0X00000001),
    LINEAR(0X00000002),
    FORCE32(0X7FFFFFFF),
    ;

    private final int value;

    FilterMode(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
