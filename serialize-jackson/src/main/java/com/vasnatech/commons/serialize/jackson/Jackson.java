package com.vasnatech.commons.serialize.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public interface Jackson {

    TypeReference<Map<String, ?>> TYPE_MAP = new TypeReference<>() {};

    ObjectMapper getObjectMapper();

    default <T> JavaType toJavaType(Class<T> parametrized, Class<?>[] parameterClasses) {
        return getObjectMapper().getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }
}
