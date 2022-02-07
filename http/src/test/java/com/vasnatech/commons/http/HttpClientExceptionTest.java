package com.vasnatech.commons.http;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpClientExceptionTest {

    static Random RANDOM = new Random();

    static int randomHttpStatus() {
        return RANDOM.nextInt(500) + 100;
    }

    static String randomString() {
        return RandomStringUtils.random(RANDOM.nextInt(20) + 20);
    }

    static Throwable randomThrowable() {
        return switch (RANDOM.nextInt(5)) {
            case 0 -> new FileNotFoundException();
            case 1 -> new UnsupportedOperationException();
            case 2 -> new UnsupportedEncodingException();
            case 3 -> new IndexOutOfBoundsException();
            case 4 -> new IllegalArgumentException();
            default -> new RuntimeException();
        };
    }

    @Test
    void constructor_01() {
        int httpCode = randomHttpStatus();
        HttpClientException e = new HttpClientException(httpCode);

        assertThat(e.getHttpStatus()).isEqualTo(httpCode);
    }

    @Test
    void constructor_02() {
        int httpCode = randomHttpStatus();
        String message = randomString();
        HttpClientException e = new HttpClientException(httpCode, message);

        assertThat(e.getHttpStatus()).isEqualTo(httpCode);
        assertThat(e.getMessage()).isEqualTo(message);
    }

    @Test
    void constructor_03() {
        int httpCode = randomHttpStatus();
        String message = randomString();
        Throwable cause = randomThrowable();
        HttpClientException e = new HttpClientException(httpCode, message, cause);

        assertThat(e.getHttpStatus()).isEqualTo(httpCode);
        assertThat(e.getMessage()).isEqualTo(message);
        assertThat(e.getCause()).isEqualTo(cause);
    }

    @Test
    void constructor_04_01() {
        int httpCode = randomHttpStatus();
        String message = randomString();
        Throwable cause = randomThrowable();
        HttpClientException e = new HttpClientException(httpCode, message, cause, false, true);

        assertThat(e.getHttpStatus()).isEqualTo(httpCode);
        assertThat(e.getMessage()).isEqualTo(message);
        assertThat(e.getCause()).isEqualTo(cause);
        assertThat(e.getStackTrace()).isNotEmpty();
    }

    @Test
    void constructor_04_02() {
        int httpCode = randomHttpStatus();
        String message = randomString();
        Throwable cause = randomThrowable();
        HttpClientException e = new HttpClientException(httpCode, message, cause, false, false);

        assertThat(e.getHttpStatus()).isEqualTo(httpCode);
        assertThat(e.getMessage()).isEqualTo(message);
        assertThat(e.getCause()).isEqualTo(cause);
        assertThat(e.getStackTrace()).isEmpty();
    }
}
