package io.github.bahaa.webgpu.samples.platform.windwos;

import io.github.bahaa.webgpu.samples.platform.PlatformException;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public enum Windows {
    ;

    public static MemorySegment getModuleHandle(final MemorySegment lpModuleName) {
        try {
            return (MemorySegment) Meta.GetModuleHandle.invokeExact(lpModuleName);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    private enum Meta {
        ;
        static final Arena LIBRARY_ARENA = Arena.ofAuto();

        static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup
                .libraryLookup(System.mapLibraryName("Kernel32"), LIBRARY_ARENA)
                .or(SymbolLookup.loaderLookup())
                .or(Linker.nativeLinker().defaultLookup());

        private static final MethodHandle GetModuleHandle;

        static {
            final var linker = Linker.nativeLinker();

            GetModuleHandle = linker.downcallHandle(SYMBOL_LOOKUP.find("GetModuleHandleW").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS));
        }
    }
}
