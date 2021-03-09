package com.vasnatech.commons.primitive;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface Bytes {

    static ByteBuffer toByteBuffer(byte[] bytes) {
        return ByteBuffer.wrap(bytes);
    }

    static String toString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    static String toString(byte[] bytes, Charset charset) {
        return new String(bytes, charset);
    }


    static byte[] fromByteBuffer(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return bytes;
    }

    static byte[] fromString(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    static byte[] fromString(String string, Charset charset) {
        return string.getBytes(charset);
    }

    static byte[] fromInputStream(InputStream in) throws IOException {
        return fromInputStream(in, 4096);
    }

    static byte[] fromInputStream(InputStream in, int bufferSize) throws IOException {
        int available = in.available();
        byte[] buffer;
        if (available == 0) //not sure how many bytes are there in the stream
            buffer = new byte[bufferSize];
        else
            buffer = new byte[available];
        int offset = 0;
        int n;
        while (-1 != (n = in.read(buffer, offset, buffer.length - offset))) {
            offset += n;
            if (offset == buffer.length) { //ensure capacity
                long newSize = Math.min(buffer.length * 2L, Integer.MAX_VALUE);
                if (newSize == buffer.length) //can't allocate more memory. array limitation.
                    throw new OutOfMemoryError();
                byte[] newBuffer = new byte[(int)newSize];
                System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
                buffer = newBuffer;
            }
        }
        if (offset < buffer.length) {
            byte[] result = new byte[offset];
            System.arraycopy(buffer, 0, result, 0, offset);
            return result;
        }
        return buffer;
    }

    static void writeToOutputStream(OutputStream out, byte[] bytes) throws IOException {
        out.write(bytes);
    }
}
