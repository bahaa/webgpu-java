package io.github.bahaa.webgpu.api.model;

public enum PowerPreference {
    UNDEFINED(0x00000000),
    LOW_POWER(0x00000001),
    HIGH_PERFORMANCE(0x00000002),
    ;
    private final int value;

    PowerPreference(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
