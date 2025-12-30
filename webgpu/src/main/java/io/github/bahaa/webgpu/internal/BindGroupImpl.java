package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.BindGroup;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuBindGroupRelease;
import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuBindGroupSetLabel;

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

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuBindGroupSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
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
