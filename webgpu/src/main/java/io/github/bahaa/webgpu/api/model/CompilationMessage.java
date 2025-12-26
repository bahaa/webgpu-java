package io.github.bahaa.webgpu.api.model;

public record CompilationMessage(String message, CompilationMessageType type,
                                 long lineNum, long linePos, long offset, long length) {
}
