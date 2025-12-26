package io.github.bahaa.webgpu.samples.platform.macos.foundation;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public enum Foundation {
    ;

    public static void retain(final MemorySegment id) throws Throwable {
        Meta.cfRetain.invokeExact(id);
    }

    private enum Meta {
        ;

        public static final MethodHandle cfRetain;

        static {
            final var arena = Arena.ofAuto();

            final var linker = Linker.nativeLinker();
            final var foundation = SymbolLookup.libraryLookup("/System/Library/Frameworks/CoreFoundation.framework/CoreFoundation", arena);

            cfRetain = linker.downcallHandle(foundation.findOrThrow("CFRetain"),
                    FunctionDescriptor.ofVoid(ValueLayout.ADDRESS));
        }
    }
}
