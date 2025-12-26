package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.BindGroupLayout;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuBindGroupLayoutRelease;
import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuBindGroupLayoutSetLabel;

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

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuBindGroupLayoutSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
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
