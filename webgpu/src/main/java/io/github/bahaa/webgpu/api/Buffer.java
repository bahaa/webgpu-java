package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.BufferMapState;
import io.github.bahaa.webgpu.api.model.BufferUsage;
import io.github.bahaa.webgpu.api.model.MapMode;

import java.lang.foreign.MemorySegment;
import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;

public interface Buffer extends ObjectBase {

    long size();

    BufferMapState mapState();

    EnumSet<BufferUsage> usage();

    MemorySegment getMappedRange(final long offset, final long size);

    default MemorySegment getMappedRange(final long offset) {
        return getMappedRange(offset, size());
    }

    default MemorySegment getMappedRange() {
        return getMappedRange(0, size());
    }

    MemorySegment getConstMappedRange(long offset, long size);

    CompletableFuture<Void> mapAsync(EnumSet<MapMode> mode, long offset, long size);

    void unmap();
}
