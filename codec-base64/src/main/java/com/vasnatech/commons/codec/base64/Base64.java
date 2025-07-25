package com.vasnatech.commons.codec.base64;

import com.vasnatech.commons.function.CheckedConsumer;
import com.vasnatech.commons.function.CheckedFunction;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Base64 {

    public OutputStream encodeStream(OutputStream out) {
        return new Base64OutputStream(out, true, -1, null);
    }

    public byte[] encode(byte[] bytes) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(bytes.length);
        write(out, o -> o.write(bytes));
        return out.toByteArray();
    }

    public String encode(String str) throws IOException {
        return new String(encode(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public <T> void write(OutputStream out, CheckedConsumer<OutputStream, IOException> writer) throws IOException {
        try (OutputStream encodeStream = encodeStream(out)) {
            writer.accept(encodeStream);
        }
    }
    
    static InputStream decodeStream(InputStream in) {
        return new Base64InputStream(in, false);
    }

    public byte[] decode(byte[] bytes) throws IOException {
        return read(new ByteArrayInputStream(bytes), InputStream::readAllBytes);
    }

    public String decode(String str) throws IOException {
        return new String(decode(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public <T> T read(InputStream in, CheckedFunction<InputStream, T, IOException> reader) throws IOException {
        try (InputStream decodeStream = decodeStream(in)) {
            return reader.apply(decodeStream);
        }
    }
}
