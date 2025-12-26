package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Texture;
import io.github.bahaa.webgpu.api.model.SurfaceGetCurrentTextureStatus;
import io.github.bahaa.webgpu.api.model.SurfaceTexture;
import io.github.bahaa.webgpu.ffm.WGPUSurfaceTexture;

import java.lang.foreign.MemorySegment;

class SurfaceTextureImpl implements SurfaceTexture {
    private final Texture texture;
    private final SurfaceGetCurrentTextureStatus status;

    private SurfaceTextureImpl(final Texture texture, final SurfaceGetCurrentTextureStatus status) {
        this.texture = texture;
        this.status = status;
    }

    public static SurfaceTexture from(final MemorySegment struct) {
        return new SurfaceTextureImpl(TextureImpl.from(WGPUSurfaceTexture.texture(struct)),
                SurfaceGetCurrentTextureStatus.fromValue(WGPUSurfaceTexture.status(struct)));
    }

    @Override
    public Texture texture() {
        return this.texture;
    }

    @Override
    public SurfaceGetCurrentTextureStatus status() {
        return this.status;
    }
}
