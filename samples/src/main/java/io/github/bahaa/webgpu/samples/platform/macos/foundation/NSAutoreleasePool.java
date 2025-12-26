package io.github.bahaa.webgpu.samples.platform.macos.foundation;

import io.github.bahaa.webgpu.samples.platform.macos.runtime.NSObject;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class NSAutoreleasePool extends NSObject {

    protected NSAutoreleasePool(final MemorySegment id) {
        super(id);
    }

    public static NSAutoreleasePool create() {
        final var id = alloc(Meta.CLASS);
        Runtime.msgSend(id, NSObject.Meta.INIT);
        return new NSAutoreleasePool(id);
    }

    public static void addObject(final NSObject object) {
        Runtime.msgSend(Meta.CLASS, Meta.ADD_OBJECT, object.id());
    }

    public void add(final NSObject object) {
        Runtime.msgSend(id(), Meta.ADD_OBJECT, object.id());
    }

    public void drain() {
        Runtime.msgSend(this.id, Meta.DRAIN);
    }

    protected enum Meta {
        ;
        public static final MemorySegment CLASS;

        public static final MemorySegment DRAIN;

        public static final MemorySegment ADD_OBJECT;

        static {
            try (final var arena = Arena.ofConfined()) {
                CLASS = Runtime.getClass(arena, "NSAutoreleasePool");

                DRAIN = Runtime.registerName(arena, "drain");
                ADD_OBJECT = Runtime.registerName(arena, "addObject:");
            }
        }
    }
}
