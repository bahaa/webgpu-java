package io.github.bahaa.webgpu.internal;

/// The callback mode controls how a callback for an asynchronous operation may be fired.
enum CallbackMode {
    WAIT_ANY_ONLY(0X00000001),
    ALLOW_PROCESS_EVENTS(0X00000002),
    ALLOW_SPONTANEOUS(0X00000003);
    private final int value;

    CallbackMode(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
