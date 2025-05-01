package com.vasnatech.commons.yaml.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vasnatech.commons.serialize.Decoder;
import com.vasnatech.commons.serialize.Encoder;
import com.vasnatech.commons.serialize.jackson.Jackson;
import com.vasnatech.commons.serialize.jackson.JacksonDecoder;
import com.vasnatech.commons.serialize.jackson.JacksonEncoder;
import com.vasnatech.commons.yaml.Yaml;

import java.util.Map;

public class YamlJackson implements Jackson {

    static final TypeReference<Map<String, ?>> TYPE_MAP = new TypeReference<>() {};

    static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper(new YAMLFactory())
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
        Yaml.setYaml(yaml());
    }

    public static Yaml yaml() {
        return new Yaml(new YamlJacksonEncoder(), new YamlJacksonDecoder());
    }

    static class YamlJacksonDecoder extends YamlJackson implements JacksonDecoder, Decoder {}
    static class YamlJacksonEncoder extends YamlJackson implements JacksonEncoder, Encoder {}
}
