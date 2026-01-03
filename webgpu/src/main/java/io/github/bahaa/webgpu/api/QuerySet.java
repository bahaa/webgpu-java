package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.QueryType;

public interface QuerySet extends ObjectBase {

    int count();

    QueryType type();
}
