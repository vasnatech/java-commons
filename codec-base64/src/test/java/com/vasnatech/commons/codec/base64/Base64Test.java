package com.vasnatech.commons.codec.base64;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class Base64Test {

    static final String MESSAGE = "The quick brown fox jumps over the lazy dog 1234567890 !@#$%^&*()[]{}-_`~/?|=+;:,<.>'";

    static Base64 base64;

    @BeforeAll
    static void beforeAll() {
        base64 = new Base64();
    }

    @Test
    void write_read() throws IOException {
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(1024);
        base64.write(
                byteArrayOut,
                out -> out.write(MESSAGE.getBytes(StandardCharsets.UTF_8))
        );

        ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(byteArrayOut.toByteArray());
        String actual = base64.read(
                byteArrayIn,
                in -> new String(in.readAllBytes(), StandardCharsets.UTF_8)
        );

        assertThat(actual).isEqualTo(MESSAGE);
    }

    @Test
    void encode_decode() throws IOException {
        String actual = base64.decode(base64.encode(MESSAGE));

        assertThat(actual).isEqualTo(MESSAGE);
    }
}
