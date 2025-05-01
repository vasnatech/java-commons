package com.vasnatech.commons.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

public interface Decoder extends Deserializer {

    <T, R extends T> R fromByteArray(byte[] bytes, Class<T> parametrized, Class<?>... parameterClasses) throws IOException;

    <T, R extends T> R fromString(String jsonString, Class<T> parametrized, Class<?>... parameterClasses) throws IOException;

    <T, R extends T> R fromInputStream(InputStream in, Class<T> parametrized, Class<?>... parameterClasses) throws IOException;

    <T, R extends T> R fromReader(Reader reader, Class<T> parametrized, Class<?>... parameterClasses) throws IOException;


    default Map<String, Object> fromByteArrayToMap(byte[] bytes) throws IOException {
        return fromByteArray(bytes, Map.class, String.class, Object.class);
    }

    default Map<String, Object> fromStringToMap(String jsonString) throws IOException {
        return fromString(jsonString, Map.class, String.class, Object.class);
    }

    default Map<String, Object> fromInputStreamToMap(InputStream in) throws IOException {
        return fromInputStream(in, Map.class, String.class, Object.class);
    }

    default Map<String, Object> fromReaderToMap(Reader reader) throws IOException {
        return fromReader(reader, Map.class, String.class, Object.class);
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
