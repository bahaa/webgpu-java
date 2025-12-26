package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.CompilationInfo;

import java.util.concurrent.CompletableFuture;

public interface ShaderModule extends ObjectBase {
    CompletableFuture<CompilationInfo> getCompilationInfo();
}
