package com.vasnatech.commons.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface Serializer {

    <T> void serialize(OutputStream out, T value) throws IOException;

    default <T> byte[] toByteArray(T value) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        serialize(out, value);
        return out.toByteArray();
    }
}
