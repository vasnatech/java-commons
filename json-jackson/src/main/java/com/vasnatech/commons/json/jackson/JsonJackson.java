package com.vasnatech.commons.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vasnatech.commons.json.Json;
import com.vasnatech.commons.serialize.Decoder;
import com.vasnatech.commons.serialize.Encoder;
import com.vasnatech.commons.serialize.jackson.Jackson;
import com.vasnatech.commons.serialize.jackson.JacksonDecoder;
import com.vasnatech.commons.serialize.jackson.JacksonEncoder;

import java.util.Map;

public class JsonJackson implements Jackson {

    static final TypeReference<Map<String, ?>> TYPE_MAP = new TypeReference<>() {};

    static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.INDENT_OUTPUT, false)
            .findAndRegisterModules();

    static ObjectMapper OBJECT_MAPPER = DEFAULT_OBJECT_MAPPER;

    public ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        OBJECT_MAPPER = objectMapper == null ? DEFAULT_OBJECT_MAPPER : objectMapper;
    }

    public static void init() {
        init(DEFAULT_OBJECT_MAPPER);
    }

    public static void init(ObjectMapper objectMapper) {
        setObjectMapper(objectMapper);
        Json.setJson(json());
    }

    public static Json json() {
        return new Json(new JsonJacksonEncoder(), new JsonJacksonDecoder());
    }

    static class JsonJacksonDecoder extends JsonJackson implements JacksonDecoder, Decoder {}
    static class JsonJacksonEncoder extends JsonJackson implements JacksonEncoder, Encoder {}
}
