package com.vasnatech.commons.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ObjectDeserializer implements Deserializer {

    @Override
    @SuppressWarnings("unchecked")
    public <T, R extends T> R deserialize(InputStream in, Class<T> rawClass, Class<?>... parameterClasses) throws IOException {
        try (ObjectInputStream objectIn = new ObjectInputStream(in)) {
            return (R) rawClass.cast(objectIn.readObject());
        } catch (ClassNotFoundException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
