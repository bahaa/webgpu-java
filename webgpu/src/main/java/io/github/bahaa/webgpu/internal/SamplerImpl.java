package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Sampler;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuSamplerRelease;
import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuSamplerSetLabel;

class SamplerImpl extends ObjectBaseImpl implements Sampler {

    protected SamplerImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static SamplerImpl from(final MemorySegment pointer) {
        return new SamplerImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuSamplerSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
    }

    protected static class Cleaner extends ObjectCleaner {
        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuSamplerRelease(pointer);
        }
    }
}
