package com.vasnatech.commons.serialize;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectSerializer implements Serializer {

    @Override
    public <T> void serialize(OutputStream out, T value) throws IOException {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(out)) {
            objectOut.writeObject(value);
        }
    }
}
