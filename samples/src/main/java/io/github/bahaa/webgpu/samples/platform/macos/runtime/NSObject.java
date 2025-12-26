package io.github.bahaa.webgpu.samples.platform.macos.runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class NSObject {

    protected final MemorySegment id;

    protected NSObject(final MemorySegment id) {
        this.id = id;
    }

    public static NSObject from(final MemorySegment id) {
        return new NSObject(id);
    }

    public static MemorySegment alloc(final MemorySegment clazz) {
        return Runtime.msgSend(clazz, Meta.ALLOC);
    }

    public void retain() {
        Runtime.msgSend(id(), Meta.RETAIN);
    }

    public void release() {
        Runtime.msgSend(id(), Meta.RELEASE);
    }

    public MemorySegment id() {
        return this.id;
    }

    protected enum Meta {
        ;
        public static final MemorySegment ALLOC;
        public static final MemorySegment INIT;
        public static final MemorySegment RETAIN;
        public static final MemorySegment RELEASE;

        static {
            try (final var arena = Arena.ofConfined()) {
                ALLOC = Runtime.registerName(arena, "alloc");
                INIT = Runtime.registerName(arena, "init");
                RETAIN = Runtime.registerName(arena, "retain");
                RELEASE = Runtime.registerName(arena, "release");
            }
        }
    }
}
