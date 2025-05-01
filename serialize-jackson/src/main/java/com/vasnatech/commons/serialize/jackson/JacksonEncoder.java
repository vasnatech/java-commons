package com.vasnatech.commons.serialize.jackson;

import com.vasnatech.commons.serialize.Encoder;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface JacksonEncoder extends Jackson, Encoder {

    default  <T, R extends T> R encode(Object obj, Class<T> rawClass, Class<?>... parameterClasses) {
        return getObjectMapper().convertValue(obj, toJavaType(rawClass, parameterClasses));
    }

    default String toString(Object obj) throws IOException {
        return getObjectMapper().writeValueAsString(obj);
    }

    default Map<String, ?> toMap(String jsonString) throws IOException {
        return getObjectMapper().readValue(jsonString, TYPE_MAP);
    }

    @Override
    default  <T> void serialize(OutputStream out, T value) throws IOException {
        getObjectMapper().writeValue(out, value);
    }
}
