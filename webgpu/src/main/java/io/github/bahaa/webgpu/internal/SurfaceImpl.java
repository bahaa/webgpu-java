package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Adapter;
import io.github.bahaa.webgpu.api.Surface;
import io.github.bahaa.webgpu.api.WebGpuException;
import io.github.bahaa.webgpu.api.model.SurfaceCapabilities;
import io.github.bahaa.webgpu.api.model.SurfaceConfiguration;
import io.github.bahaa.webgpu.api.model.SurfaceTexture;
import io.github.bahaa.webgpu.ffm.WGPUSurfaceCapabilities;
import io.github.bahaa.webgpu.ffm.WGPUSurfaceTexture;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class SurfaceImpl extends ObjectBaseImpl implements Surface {

    protected SurfaceImpl(final MemorySegment pointer) {
        super(pointer);
    }

    public static Surface from(final MemorySegment pointer) {
        return new SurfaceImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public SurfaceCapabilities capabilities(final Adapter adapter) {
        try (final var arena = Arena.ofConfined()) {
            final var struct = WGPUSurfaceCapabilities.allocate(arena);
            final var status = wgpuSurfaceGetCapabilities(this.pointer(), adapter.pointer(),
                    MemorySegment.ofAddress(struct.address()));

            if (status != 1) {
                throw new WebGpuException("wgpuSurfaceGetCapabilities failed with status %d".formatted(status));
            }

            return SurfaceCapabilities.from(struct);
        }
    }

    @Override
    public void configure(final SurfaceConfiguration configuration) {
        try (final var arena = Arena.ofConfined()) {
            wgpuSurfaceConfigure(this.pointer(), configuration.toSegmentAddress(arena));
        }
    }

    @Override
    public SurfaceTexture currentTexture() {
        try (final var arena = Arena.ofConfined()) {
            final var struct = WGPUSurfaceTexture.allocate(arena);
            wgpuSurfaceGetCurrentTexture(pointer(), MemorySegment.ofAddress(struct.address()));
            return SurfaceTextureImpl.from(struct);
        }
    }

    @Override
    public void present() {
        final var status = wgpuSurfacePresent(pointer());

        if (status != 1) {
            throw new WebGpuException("wgpuSurfacePresent failed with status %d".formatted(status));
        }
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuSurfaceRelease(pointer);
        }
    }
}
