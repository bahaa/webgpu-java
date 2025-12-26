module io.github.bahaa.webgpu {
    requires io.github.bahaa.webgpu.wgpu;
    requires io.helidon.builder.api;
    requires org.jspecify;
    requires java.desktop;

    exports io.github.bahaa.webgpu.api;
    exports io.github.bahaa.webgpu.api.model;
    exports io.github.bahaa.webgpu.tools;
}