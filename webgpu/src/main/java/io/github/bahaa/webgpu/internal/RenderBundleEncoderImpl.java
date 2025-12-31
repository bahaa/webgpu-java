package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.RenderBundleEncoder;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuRenderBundleEncoderRelease;
import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuRenderBundleEncoderSetLabel;

class RenderBundleEncoderImpl extends ObjectBaseImpl implements RenderBundleEncoder {

    protected RenderBundleEncoderImpl(final MemorySegment pointer) {
        super(pointer);
    }

    public static RenderBundleEncoder from(final MemorySegment pointer) {
        return new RenderBundleEncoderImpl(pointer);
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuRenderBundleEncoderSetLabel(pointer(), StringView.from(label).toSegment(arena));
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
            wgpuRenderBundleEncoderRelease(pointer);
        }
    }
}
