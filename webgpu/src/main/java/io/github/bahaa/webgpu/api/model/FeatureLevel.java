package io.github.bahaa.webgpu.api.model;

public enum FeatureLevel {
    COMPATIBILITY(0x00000001),
    CORE(0x00000002),
    ;
    private final int value;

    FeatureLevel(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
