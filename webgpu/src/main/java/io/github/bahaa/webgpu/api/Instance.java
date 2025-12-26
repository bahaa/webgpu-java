package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.InstanceDescriptor;
import io.github.bahaa.webgpu.api.model.RequestAdapterOptions;
import io.github.bahaa.webgpu.api.model.SurfaceDescriptor;
import io.github.bahaa.webgpu.internal.InstanceImpl;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface Instance extends ObjectBase {

    /**
     * Create a WGPUInstance
     */
    static Instance create() {
        return InstanceImpl.create(null);
    }

    /**
     * Create a WGPUInstance
     */
    static Instance create(final InstanceDescriptor descriptor) {
        return InstanceImpl.create(descriptor);
    }

    /**
     * Create a WGPUInstance
     */
    static Instance create(final Consumer<InstanceDescriptor.Builder> consumer) {
        return create(builder().update(consumer).build());
    }

    static InstanceDescriptor.Builder builder() {
        return InstanceDescriptor.builder();
    }

    /**
     * TODO
     *
     * @param options
     * @return
     */
    CompletableFuture<Adapter> requestAdapter(final RequestAdapterOptions options);

    /**
     *
     * @param surfaceDescriptor
     * @return
     */
    Surface createSurface(final SurfaceDescriptor surfaceDescriptor);
}
