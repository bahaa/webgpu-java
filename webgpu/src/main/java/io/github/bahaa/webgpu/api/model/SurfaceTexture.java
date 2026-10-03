package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.Texture;
import org.jspecify.annotations.Nullable;

public interface SurfaceTexture {

    @Nullable Texture texture();

    SurfaceGetCurrentTextureStatus status();
}
