package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.TextureView;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuTextureViewRelease;

class TextureViewImpl extends ObjectBaseImpl implements TextureView {
    protected TextureViewImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static TextureViewImpl from(final MemorySegment pointer) {
        return new TextureViewImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuTextureViewRelease(pointer);
        }
    }
}
