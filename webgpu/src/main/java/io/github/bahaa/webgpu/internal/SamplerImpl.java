package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Sampler;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuSamplerRelease;

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
