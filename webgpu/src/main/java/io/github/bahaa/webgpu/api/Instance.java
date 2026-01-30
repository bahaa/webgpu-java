package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.InstanceDescriptor;
import io.github.bahaa.webgpu.api.model.RequestAdapterOptions;
import io.github.bahaa.webgpu.api.model.SurfaceDescriptor;
import io.github.bahaa.webgpu.internal.InstanceImpl;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/// Contains the various entry points to start interacting with the system’s GPUs.
public interface Instance extends NativeObject {

    /// Create WebGPU instance using defaults.
    static Instance create() {
        return InstanceImpl.create(null);
    }

    /// Create WebGPU instance using the provided descriptor
    ///
    /// @param descriptor [InstanceDescriptor] object to configure the instance
    static Instance create(final InstanceDescriptor descriptor) {
        return InstanceImpl.create(descriptor);
    }

    /// Create WebGPU instance using the descriptor created by the supplied builder
    ///
    /// @param consumer a [Consumer] that supplies a builder to build [InstanceDescriptor]
    static Instance create(final Consumer<InstanceDescriptor.Builder> consumer) {
        return create(InstanceDescriptor.builder().update(consumer).build());
    }

    /// Request a WebGPU [Adapter] asynchronously. The adapter can be used to create a [Device]
    ///
    /// @param options [RequestAdapterOptions] for the requested adapter
    CompletableFuture<Adapter> requestAdapter(final RequestAdapterOptions options);

    Surface createSurface(final SurfaceDescriptor surfaceDescriptor);
}
