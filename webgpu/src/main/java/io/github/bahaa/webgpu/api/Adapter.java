package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.AdapterInfo;
import io.github.bahaa.webgpu.api.model.DeviceDescriptor;
import io.github.bahaa.webgpu.api.model.FeatureName;

import java.util.concurrent.CompletableFuture;

public interface Adapter extends NativeObject {

    CompletableFuture<Device> requestDevice(final DeviceDescriptor deviceDescriptor);

    AdapterInfo adapterInfo();

    boolean hasFeature(FeatureName feature);
}
