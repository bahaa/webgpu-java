package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.Texture;

public interface SurfaceTexture {
    Texture texture();

    SurfaceGetCurrentTextureStatus status();
}
