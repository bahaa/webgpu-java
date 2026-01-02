package io.github.bahaa.webgpu.api.model;

public enum BufferMapState {
    UNMAPPED(0X00000001),
    PENDING(0X00000002),
    MAPPED(0x00000003),
    ;
    private final int value;

    BufferMapState(final int value) {
        this.value = value;
    }

    public static BufferMapState valueOf(final int value) {
        for (final var state : values()) {
            if (state.value == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("invalid BufferMapState value: " + value);
    }

    public int value() {
        return this.value;
    }
}
