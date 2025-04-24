package com.vasnatech.commons.properties.jackson;

import com.vasnatech.commons.properties.Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class JacksonEncoder extends Jackson implements Encoder {

    @Override
    public <T, R extends T> R encode(Object obj, Class<T> rawClass, Class<?>... parameterClasses) {
        return OBJECT_MAPPER.convertValue(obj, toJavaType(rawClass, parameterClasses));
    }

    @Override
    public String toString(Object obj) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }


    @Override
    public <T> void serialize(OutputStream out, T value) throws IOException {
        OBJECT_MAPPER.writeValue(out, value);
    }
}
