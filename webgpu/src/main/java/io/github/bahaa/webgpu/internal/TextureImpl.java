package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Texture;
import io.github.bahaa.webgpu.api.TextureView;
import io.github.bahaa.webgpu.api.model.TextureViewDescriptor;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuTextureCreateView;
import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuTextureRelease;

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
