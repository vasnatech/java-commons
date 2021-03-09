package com.vasnatech.commons.serialize;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface Deserializer {

    <T, R extends T> R deserialize(InputStream in, Class<T> rawClass, Class<?>... parameterClasses) throws IOException;

    default <T, R extends T> R deserialize(byte[] bytes, Class<T> rawClass, Class<?>... parameterClasses) throws IOException{
        return deserialize(new ByteArrayInputStream(bytes), rawClass, parameterClasses);
    }
}
