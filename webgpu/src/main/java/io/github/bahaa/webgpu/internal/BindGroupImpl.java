package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.BindGroup;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuBindGroupRelease;

class BindGroupImpl extends ObjectBaseImpl implements BindGroup {
    protected BindGroupImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static BindGroupImpl from(final MemorySegment pointer) {
        return new BindGroupImpl(pointer);
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
            wgpuBindGroupRelease(pointer);
        }
    }
}
