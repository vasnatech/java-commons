package com.vasnatech.commons.primitive;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BytesTest {

    static Random RANDOM = new Random();
    static String STRING = "The quick brown fox jumps over the lazy dog";

    static byte[] bytes() {
        return bytes(RANDOM.nextInt(1024));
    }

    static byte[] bytes(int length) {
        byte[] bytes = new byte[length];
        RANDOM.nextBytes(bytes);
        return bytes;
    }

    @Test
    void byteBuffer() {
        byte[] bytes = bytes();
        assertArrayEquals(bytes, Bytes.fromByteBuffer(Bytes.toByteBuffer(bytes)));
    }

    @Test
    void string() {
        assertEquals(STRING, Bytes.toString(Bytes.fromString(STRING)));
    }

    @Test
    void string_with_encoding() {
        assertEquals(STRING, Bytes.toString(Bytes.fromString(STRING, StandardCharsets.US_ASCII), StandardCharsets.US_ASCII));
        assertEquals(STRING, Bytes.toString(Bytes.fromString(STRING, StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1));
        assertEquals(STRING, Bytes.toString(Bytes.fromString(STRING, StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        assertEquals(STRING, Bytes.toString(Bytes.fromString(STRING, StandardCharsets.UTF_16BE), StandardCharsets.UTF_16BE));
        assertEquals(STRING, Bytes.toString(Bytes.fromString(STRING, StandardCharsets.UTF_16LE), StandardCharsets.UTF_16LE));
        assertEquals(STRING, Bytes.toString(Bytes.fromString(STRING, StandardCharsets.UTF_16), StandardCharsets.UTF_16));
    }
}
