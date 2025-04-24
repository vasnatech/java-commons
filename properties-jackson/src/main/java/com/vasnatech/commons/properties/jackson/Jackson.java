package com.vasnatech.commons.properties.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.vasnatech.commons.properties.Properties;

import java.util.Map;

public class Jackson {

    static final JavaPropsMapper OBJECT_MAPPER = (JavaPropsMapper) new JavaPropsMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    static final TypeReference<Map<String, ?>> TYPE_MAP = new TypeReference<>() {};

    public static void init() {
        Properties.setProperties(new Properties(new JacksonEncoder(), new JacksonDecoder()));
    }

    static <T> JavaType toJavaType(Class<T> parametrized, Class<?>[] parameterClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }


    public static Properties properties() {
        return new Properties(new JacksonEncoder(), new JacksonDecoder());
    }
}
