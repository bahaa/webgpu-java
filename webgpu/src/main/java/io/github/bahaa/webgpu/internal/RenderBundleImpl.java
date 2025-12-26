package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.RenderBundle;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuRenderBundleRelease;
import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuRenderBundleSetLabel;

class RenderBundleImpl extends ObjectBaseImpl implements RenderBundle {

    protected RenderBundleImpl(final MemorySegment pointer) {
        super(pointer);
    }

    public static RenderBundleImpl from(final MemorySegment pointer) {
        return new RenderBundleImpl(pointer);
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuRenderBundleSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
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
            wgpuRenderBundleRelease(pointer);
        }
    }
}
