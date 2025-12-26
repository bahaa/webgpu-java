package io.github.bahaa.webgpu.api.model;

public enum SType {
    SHADER_SOURCE_SPIRV(0X00000001),
    SHADER_SOURCE_WGSL(0X00000002),
    RENDER_PASS_MAX_DRAW_COUNT(0X00000003),
    SURFACE_SOURCE_METAL_LAYER(0X00000004),
    SURFACE_SOURCE_WINDOWS_HWND(0X00000005),
    SURFACE_SOURCE_XLIB_WINDOW(0X00000006),
    SURFACE_SOURCE_WAYLAND_SURFACE(0X00000007),
    SURFACE_SOURCE_ANDROID_NATIVE_WINDOW(0X00000008),
    SURFACE_SOURCE_XCB_WINDOW(0X00000009),
    ;

    private final int value;

    SType(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
