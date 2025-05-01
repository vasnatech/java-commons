package com.vasnatech.commons.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface Encoder extends Serializer {

    <T, R extends T> R encode(Object obj, Class<T> rawClass, Class<?>... parameterClasses) throws IOException;

    default byte[] toByteArray(Object obj) throws IOException {
        return encode(obj, byte[].class);
    }

    default String toString(Object obj) throws IOException {
        return encode(obj, String.class);
    }

    default Map<String, Object> toMap(Object obj) throws IOException {
        return encode(obj, Map.class, String.class, Object.class);
    }


    @Override
    default <T> void serialize(OutputStream out, T value) throws IOException {
        out.write(toByteArray(value));
    }
}
