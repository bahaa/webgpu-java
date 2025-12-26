package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.BindGroupLayout;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuBindGroupLayoutRelease;

class BindGroupLayoutImpl extends ObjectBaseImpl implements BindGroupLayout {

    protected BindGroupLayoutImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static BindGroupLayoutImpl from(final MemorySegment pointer) {
        return new BindGroupLayoutImpl(pointer);
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
            wgpuBindGroupLayoutRelease(pointer);
        }
    }
}
