package com.vasnatech.commons.json.jackson;

import com.vasnatech.commons.json.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class JacksonDecoder extends Jackson implements Decoder {

    @Override
    public <T, R extends T> R fromByteArray(byte[] bytes, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return OBJECT_MAPPER.readValue(bytes, toJavaType(rawClass, parameterClasses));
    }

    @Override
    public <T, R extends T> R fromString(String jsonString, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return OBJECT_MAPPER.readValue(jsonString, toJavaType(rawClass, parameterClasses));
    }

    @Override
    public <T, R extends T> R fromInputStream(InputStream in, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return OBJECT_MAPPER.readValue(in, toJavaType(rawClass, parameterClasses));
    }

    @Override
    public <T, R extends T> R fromReader(Reader reader, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return OBJECT_MAPPER.readValue(reader, toJavaType(rawClass, parameterClasses));
    }

    @Override
    public <T, R extends T> R deserialize(InputStream in, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return fromInputStream(in, rawClass, parameterClasses);
    }

    @Override
    public <T, R extends T> R deserialize(byte[] bytes, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        return fromByteArray(bytes, rawClass, parameterClasses);
    }
}
