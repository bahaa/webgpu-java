package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.InstanceDescriptor;
import io.github.bahaa.webgpu.api.model.RequestAdapterOptions;
import io.github.bahaa.webgpu.api.model.SurfaceDescriptor;
import io.github.bahaa.webgpu.internal.InstanceImpl;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/// Contains the various entry points to start interacting with the system’s GPUs.
public interface Instance extends NativeObject {

    static Instance create() {
        return InstanceImpl.create(null);
    }

    static Instance create(final InstanceDescriptor descriptor) {
        return InstanceImpl.create(descriptor);
    }

    static Instance create(final Consumer<InstanceDescriptor.Builder> consumer) {
        return create(builder().update(consumer).build());
    }

    static InstanceDescriptor.Builder builder() {
        return InstanceDescriptor.builder();
    }

    CompletableFuture<Adapter> requestAdapter(final RequestAdapterOptions options);

    Surface createSurface(final SurfaceDescriptor surfaceDescriptor);

    void processEvents();
}
