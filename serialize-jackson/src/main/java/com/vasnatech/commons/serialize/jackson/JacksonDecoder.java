package com.vasnatech.commons.serialize.jackson;

import com.vasnatech.commons.serialize.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public interface JacksonDecoder extends Jackson, Decoder {

    default  <T, R extends T> R fromByteArray(byte[] bytes, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return getObjectMapper().readValue(bytes, toJavaType(rawClass, parameterClasses));
    }

    default <T, R extends T> R fromString(String jsonString, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return getObjectMapper().readValue(jsonString, toJavaType(rawClass, parameterClasses));
    }

    default <T, R extends T> R fromInputStream(InputStream in, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return getObjectMapper().readValue(in, toJavaType(rawClass, parameterClasses));
    }

    default <T, R extends T> R fromReader(Reader reader, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return getObjectMapper().readValue(reader, toJavaType(rawClass, parameterClasses));
    }

    @Override
    default <T, R extends T> R deserialize(InputStream in, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return fromInputStream(in, rawClass, parameterClasses);
    }

    @Override
    default <T, R extends T> R deserialize(byte[] bytes, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return fromByteArray(bytes, rawClass, parameterClasses);
    }
}
