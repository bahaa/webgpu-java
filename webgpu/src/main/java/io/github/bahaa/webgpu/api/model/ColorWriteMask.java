package io.github.bahaa.webgpu.api.model;

public enum ColorWriteMask {
    NONE(0x0000000000000000),
    RED(0x0000000000000001),
    GREEN(0x0000000000000002),
    BLUE(0x0000000000000004),
    ALPHA(0x0000000000000008),
    ALL(0x000000000000000F);

    private final long value;

    ColorWriteMask(final long value) {
        this.value = value;
    }

    public long value() {
        return this.value;
    }
}
