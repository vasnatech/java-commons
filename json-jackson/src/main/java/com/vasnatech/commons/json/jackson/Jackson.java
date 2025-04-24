package com.vasnatech.commons.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasnatech.commons.json.Json;

import java.util.Map;

public class Jackson {

    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules();

    static final TypeReference<Map<String, ?>> TYPE_MAP = new TypeReference<>() {};

    public static void init() {
        Json.setJson(new Json(new JacksonEncoder(), new JacksonDecoder()));
    }

    static <T> JavaType toJavaType(Class<T> parametrized, Class<?>[] parameterClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }


    public static Json json() {
        return new Json(new JacksonEncoder(), new JacksonDecoder());
    }
}
