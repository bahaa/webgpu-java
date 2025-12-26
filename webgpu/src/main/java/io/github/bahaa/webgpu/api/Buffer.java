package io.github.bahaa.webgpu.api;

import java.lang.foreign.MemorySegment;

public interface Buffer extends ObjectBase {

    long size();

    MemorySegment getMappedRange(final long offset, final long size);

    default MemorySegment getMappedRange(final long offset) {
        return getMappedRange(offset, size());
    }

    default MemorySegment getMappedRange() {
        return getMappedRange(0, size());
    }

    void unmap();
}
