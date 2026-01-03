package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.QuerySet;
import io.github.bahaa.webgpu.api.model.QueryType;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class QuerySetImpl extends ObjectBaseImpl implements QuerySet {

    protected QuerySetImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static QuerySetImpl from(final MemorySegment pointer) {
        return new QuerySetImpl(pointer);
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuQuerySetSetLabel(this.pointer(), StringView.from(label).toSegment(arena));
        }
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public int count() {
        return wgpuQuerySetGetCount(this.pointer());
    }

    @Override
    public QueryType type() {
        return QueryType.valueOf(wgpuQuerySetGetType(this.pointer()));
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuQuerySetRelease(pointer);
        }
    }
}
