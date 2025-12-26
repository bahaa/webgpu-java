package io.github.bahaa.webgpu.samples.platform.macos.foundation;

import io.github.bahaa.webgpu.samples.platform.macos.runtime.NSObject;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

import static io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime.msgSend;

public class NSString extends NSObject {

    protected NSString(final MemorySegment id) {
        super(id);
    }

    public static NSString from(final MemorySegment id) {
        return new NSString(id);
    }

    public static NSString from(final String str) {
        try (final var arena = Arena.ofConfined()) {
            final var cstr = arena.allocateFrom(str);
            final var id = alloc(Meta.CLASS);
            return new NSString(msgSend(id, Meta.INIT_WITH_UTF8_STRING, cstr));
        }
    }

    public String toUtf8String() {
        final var cstr = msgSend(this.id, Meta.UTF8_STRING);
        return cstr.reinterpret(Integer.MAX_VALUE).getString(0, StandardCharsets.UTF_8);
    }

    protected enum Meta {
        ;
        public static final MemorySegment CLASS;

        public static final MemorySegment INIT_WITH_UTF8_STRING, UTF8_STRING;

        static {
            try (final var arena = Arena.ofConfined()) {
                CLASS = Runtime.getClass(arena, "NSString");

                INIT_WITH_UTF8_STRING = Runtime.registerName(arena, "initWithUTF8String:");
                UTF8_STRING = Runtime.registerName(arena, "UTF8String");
            }
        }
    }
}
