package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.AdapterInfo;
import io.github.bahaa.webgpu.api.model.DeviceDescriptor;
import io.github.bahaa.webgpu.api.model.FeatureName;
import io.github.bahaa.webgpu.api.model.SupportedLimits;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/// The Adapter interface of the WebGPU API represents a GPU adapter. From this you can request a [Device], adapter
/// info, features, and limits.
///
/// An Adapter object is requested using the [Instance#requestAdapter] method.
public interface Adapter extends NativeObject {

    /// Returns a Future that fulfills with a [Device] object, which is the primary interface for communicating with
    /// the GPU.
    CompletableFuture<Device> requestDevice(final DeviceDescriptor deviceDescriptor);

    /// A [AdapterInfo] object containing identifying information about the adapter.
    AdapterInfo info();

    /// A [Set] of [FeatureName] that describes additional functionality supported by the adapter.
    Set<FeatureName> features();

    /// A [SupportedLimits] object that describes the limits supported by the adapter.
    SupportedLimits limits();

    /// Returns `true` if the adapter supports the provided feature.
    boolean hasFeature(FeatureName feature);
}
