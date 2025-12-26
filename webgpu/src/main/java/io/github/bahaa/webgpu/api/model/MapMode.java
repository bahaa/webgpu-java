package io.github.bahaa.webgpu.api.model;

public enum MapMode {
    NONE(0x0000000000000000),
    READ(0x0000000000000001),
    WRITE(0x0000000000000002),
    ;
    private final long value;

    MapMode(final long value) {
        this.value = value;
    }

    public long value() {
        return this.value;
    }
}
