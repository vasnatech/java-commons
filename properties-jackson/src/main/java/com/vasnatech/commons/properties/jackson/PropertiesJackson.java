package com.vasnatech.commons.properties.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.vasnatech.commons.properties.Properties;
import com.vasnatech.commons.serialize.Decoder;
import com.vasnatech.commons.serialize.Encoder;
import com.vasnatech.commons.serialize.jackson.JacksonDecoder;
import com.vasnatech.commons.serialize.jackson.JacksonEncoder;

import java.util.Map;

public class PropertiesJackson {

    static final TypeReference<Map<String, ?>> TYPE_MAP = new TypeReference<>() {};

    static final ObjectMapper DEFAULT_OBJECT_MAPPER = (JavaPropsMapper) new JavaPropsMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
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
        OBJECT_MAPPER = objectMapper;
        Properties.setProperties(new Properties(new PropertiesJacksonEncoder(), new PropertiesJacksonDecoder()));
    }

    public static Properties properties() {
        return new Properties(new PropertiesJacksonEncoder(), new PropertiesJacksonDecoder());
    }

    static class PropertiesJacksonDecoder extends PropertiesJackson implements JacksonDecoder, Decoder {}
    static class PropertiesJacksonEncoder extends PropertiesJackson implements JacksonEncoder, Encoder {}

}
