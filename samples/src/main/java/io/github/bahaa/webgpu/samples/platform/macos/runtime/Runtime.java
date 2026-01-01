package io.github.bahaa.webgpu.samples.platform.macos.runtime;

import io.github.bahaa.webgpu.samples.platform.PlatformException;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public enum Runtime {
    ;


    public static MemorySegment getClass(final Arena arena, final String name) {
        try {
            return (MemorySegment) Meta.objc_getClass.invokeExact(arena.allocateFrom(name));
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static MemorySegment registerName(final Arena arena, final String name) {
        try {
            return (MemorySegment) Meta.sel_registerName.invokeExact(arena.allocateFrom(name));
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static void dispose(final MemorySegment object) {
        try {
            Meta.object_dispose.invokeExact(object);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel) {
        try {
            return (MemorySegment) Meta.objc_msgSend.invokeExact(self, sel);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final byte param) {
        try {
            return (MemorySegment) Meta.objc_msgSendByte.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final int param) {
        try {
            return (MemorySegment) Meta.objc_msgSendInt.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final long param) {
        try {
            return (MemorySegment) Meta.objc_msgSendLong.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final MemorySegment param) {
        try {
            return (MemorySegment) Meta.objc_msgSendAddr.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final float param) {
        try {
            return (MemorySegment) Meta.objc_msgSendFloat.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final MemorySegment title,
                                        final MemorySegment action, final MemorySegment keyEquivalent) {
        try {
            return (MemorySegment) Meta.objc_msgSendAddrAddrAddr.invokeExact(self, sel, title, action, keyEquivalent);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    public static MemorySegment classCreateInstance(final MemorySegment clazz, final int extraBytes) {
        try {
            return (MemorySegment) Meta.class_createInstance.invokeExact(clazz, extraBytes);
        } catch (final Throwable e) {
            throw new PlatformException(e);
        }
    }

    private enum Meta {
        ;
        static final Arena LIBRARY_ARENA = Arena.ofAuto();
        private static final String LIB_OBJC = "objc.A";
        static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup
                .libraryLookup(System.mapLibraryName(LIB_OBJC), LIBRARY_ARENA)
                .or(SymbolLookup.loaderLookup())
                .or(Linker.nativeLinker().defaultLookup());
        private static final MethodHandle objc_getClass;
        private static final MethodHandle sel_registerName;
        private static final MethodHandle object_dispose;
        private static final MethodHandle objc_msgSend;
        private static final MethodHandle objc_msgSendByte;
        private static final MethodHandle objc_msgSendInt;
        private static final MethodHandle objc_msgSendLong;
        private static final MethodHandle objc_msgSendFloat;
        private static final MethodHandle objc_msgSendAddr;
        private static final MethodHandle objc_msgSendAddrAddrAddr;
        private static final MethodHandle class_createInstance;

        static {
            final var linker = Linker.nativeLinker();

            objc_getClass = linker.downcallHandle(SYMBOL_LOOKUP.find("objc_getClass").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS));

            sel_registerName = linker.downcallHandle(SYMBOL_LOOKUP.find("sel_registerName").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS));

            object_dispose = linker.downcallHandle(SYMBOL_LOOKUP.find("object_dispose").orElseThrow(),
                    FunctionDescriptor.ofVoid(ValueLayout.ADDRESS));

            objc_msgSend = linker.downcallHandle(
                    SYMBOL_LOOKUP.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS),
                    Linker.Option.critical(false));

            objc_msgSendByte = linker.downcallHandle(
                    SYMBOL_LOOKUP.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_BYTE),
                    Linker.Option.critical(false));

            objc_msgSendInt = linker.downcallHandle(
                    SYMBOL_LOOKUP.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT),
                    Linker.Option.critical(false));

            objc_msgSendLong = linker.downcallHandle(
                    SYMBOL_LOOKUP.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG),
                    Linker.Option.critical(false));

            objc_msgSendFloat = linker.downcallHandle(
                    SYMBOL_LOOKUP.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_FLOAT),
                    Linker.Option.critical(false));

            objc_msgSendAddr = linker.downcallHandle(
                    SYMBOL_LOOKUP.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS),
                    Linker.Option.critical(false));

            objc_msgSendAddrAddrAddr = linker.downcallHandle(
                    SYMBOL_LOOKUP.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS,
                            ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS),
                    Linker.Option.critical(false));

            class_createInstance = linker.downcallHandle(
                    SYMBOL_LOOKUP.find("class_createInstance").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT),
                    Linker.Option.critical(false));
        }
    }
}
