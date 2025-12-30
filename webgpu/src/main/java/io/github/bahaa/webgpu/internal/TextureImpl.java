package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Texture;
import io.github.bahaa.webgpu.api.TextureView;
import io.github.bahaa.webgpu.api.model.*;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.EnumSet;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class TextureImpl extends ObjectBaseImpl implements Texture {
    protected TextureImpl(final MemorySegment pointer) {
        super(pointer);
    }

    public static TextureImpl from(final MemorySegment pointer) {
        return new TextureImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public TextureView createView(final TextureViewDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var view = wgpuTextureCreateView(this.pointer(),
                    descriptor == null ? MemorySegment.NULL : descriptor.toSegment(arena));
            assertObject(view, "wgpuTextureCreateView");
            return TextureViewImpl.from(view);
        }
    }

    @Override
    public int width() {
        return wgpuTextureGetWidth(pointer());
    }

    @Override
    public int height() {
        return wgpuTextureGetHeight(pointer());
    }

    @Override
    public int depthOrArrayLayers() {
        return wgpuTextureGetDepthOrArrayLayers(pointer());
    }

    @Override
    public int mipLevelCount() {
        return wgpuTextureGetMipLevelCount(pointer());
    }

    @Override
    public EnumSet<TextureUsage> usage() {
        return TextureUsage.fromMask(wgpuTextureGetUsage(pointer()));
    }

    @Override
    public TextureDimension dimension() {
        return TextureDimension.fromValue(wgpuTextureGetDimension(pointer()));
    }

    @Override
    public TextureFormat format() {
        return TextureFormat.fromValue(wgpuTextureGetFormat(pointer()));
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuTextureSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuTextureRelease(pointer);
        }
    }
}
