package io.github.bahaa.webgpu.api;


import io.github.bahaa.webgpu.api.model.SurfaceCapabilities;
import io.github.bahaa.webgpu.api.model.SurfaceConfiguration;
import io.github.bahaa.webgpu.api.model.SurfaceTexture;

public interface Surface extends ObjectBase {

    SurfaceCapabilities capabilities(final Adapter adapter);

    void configure(final SurfaceConfiguration configuration);

    SurfaceTexture currentTexture();

    void present();
}
