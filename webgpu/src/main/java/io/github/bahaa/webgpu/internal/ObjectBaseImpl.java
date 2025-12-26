package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.ObjectBase;

import java.lang.foreign.MemorySegment;

abstract class ObjectBaseImpl extends NativeObjectImpl implements ObjectBase {

    protected ObjectBaseImpl(final MemorySegment pointer) {
        super(pointer);
    }
}
