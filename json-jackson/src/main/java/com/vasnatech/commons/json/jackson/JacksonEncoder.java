package com.vasnatech.commons.json.jackson;

import com.vasnatech.commons.json.Encoder;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class JacksonEncoder extends Jackson implements Encoder {

    @Override
    public <T, R extends T> R encode(Object obj, Class<T> rawClass, Class<?>... parameterClasses) {
        return OBJECT_MAPPER.convertValue(obj, toJavaType(rawClass, parameterClasses));
    }

    @Override
    public String toString(Object obj) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    static Map<String, ?> toMap(String jsonString) throws IOException {
        return OBJECT_MAPPER.readValue(jsonString, TYPE_MAP);
    }


    @Override
    public <T> void serialize(OutputStream out, T value) throws IOException {
        OBJECT_MAPPER.writeValue(out, value);
    }
}
