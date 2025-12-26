package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.ShaderModule;

import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.wgpuShaderModuleRelease;

class ShaderModuleImpl extends ObjectBaseImpl implements ShaderModule {

    protected ShaderModuleImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static ShaderModuleImpl from(final MemorySegment pointer) {
        return new ShaderModuleImpl(pointer);
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
            wgpuShaderModuleRelease(pointer);
        }
    }
}
