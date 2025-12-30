package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.NativeObject;
import io.github.bahaa.webgpu.api.WebGpuException;
import io.github.bahaa.webgpu.tools.LibraryLoader;

import java.lang.foreign.MemorySegment;
import java.lang.ref.Cleaner;

public abstract class NativeObjectImpl implements NativeObject {

    protected static final Cleaner CLEANER = Cleaner.create();

    static {
        LibraryLoader.loadLibrary("wgpu_native");
    }

    private final MemorySegment pointer;
    private final Cleaner.Cleanable cleanable;

    protected NativeObjectImpl(final MemorySegment pointer) {
        this.pointer = pointer;
        this.cleanable = CLEANER.register(this, cleaner());
    }

    protected static void assertObject(final MemorySegment pointer, final String functionName) {
        if (pointer == null || MemorySegment.NULL.equals(pointer)) {
            throw new WebGpuException("`%s`  returned NULL.".formatted(functionName));
        }
    }

    protected abstract ObjectBaseImpl.ObjectCleaner cleaner();

    @Override
    public final void close() {
        this.cleanable.clean();
    }

    @Override
    public MemorySegment pointer() {
        return this.pointer;
    }

    protected abstract static class ObjectCleaner implements Runnable {

        private final MemorySegment pointer;

        protected ObjectCleaner(final MemorySegment pointer) {
            this.pointer = pointer;
        }

        @Override
        public void run() {
            this.clean(this.pointer);
        }

        protected abstract void clean(MemorySegment pointer);
    }
}
