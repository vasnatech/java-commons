package com.vasnatech.commons.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public interface Resources {

    static InputStream asInputStream(Object relativeTo, String name) {
        return asInputStream(relativeTo.getClass(), name);
    }

    static InputStream asInputStream(Class<?> relativeTo, String name) {
        return relativeTo.getResourceAsStream(name);
    }

    static InputStream asInputStream(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

    static InputStream asInputStream(Path path) throws IOException {
        return Files.newInputStream(path);
    }

    static Reader asReader(Object relativeTo, String name) {
        return asReader(asInputStream(relativeTo.getClass(), name));
    }

    static Reader asReader(Class<?> relativeTo, String name) {
        return asReader(relativeTo.getResourceAsStream(name));
    }

    static Reader asReader(String name) {
        return asReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(name));
    }

    static Reader asReader(Path path) throws IOException {
        return Files.newBufferedReader(path);
    }

    static Reader asReader(InputStream in) {
        return asReader(in, StandardCharsets.UTF_8);
    }

    static Reader asReader(InputStream in, Charset encoding) {
        return new InputStreamReader(in, encoding);
    }

    static byte[] asByteArray(Object relativeTo, String name) throws IOException {
        return asByteArray(asInputStream(relativeTo.getClass(), name));
    }

    static byte[] asByteArray(Class<?> relativeTo, String name) throws IOException {
        return asByteArray(relativeTo.getResourceAsStream(name));
    }

    static byte[] asByteArray(String name) throws IOException {
        return asByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream(name));
    }

    static byte[] asByteArray(InputStream in) throws IOException {
        return asByteArray(in, 4096);
    }

    static byte[] asByteArray(InputStream in, int bufferSize) throws IOException {
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

    static byte[] asByteArray(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    static String asString(Object relativeTo, String name) throws IOException {
        return asString(relativeTo.getClass(), name);
    }

    static String asString(Class<?> relativeTo, String name) throws IOException {
        return asString(asInputStream(relativeTo, name));
    }

    static String asString(String name) throws IOException {
        return asString(asInputStream(name));
    }

    static String asString(Path path) throws IOException {
        return Files.readString(path);
    }

    static String asString(Path path, Charset charset) throws IOException {
        return Files.readString(path, charset);
    }

    static String asString(InputStream in) throws IOException {
        return asString(in, StandardCharsets.UTF_8);
    }

    static String asString(InputStream in, Charset encoding) throws IOException {
        return asString(in, encoding, 4096);
    }

    static String asString(InputStream in, Charset encoding, int bufferSize) throws IOException {
        return asStringBuilder(in, encoding, bufferSize).toString();
    }

    static String asString(Reader reader) throws IOException {
        return asString(reader, 4096);
    }

    static String asString(Reader reader, int bufferSize) throws IOException {
        return asStringBuilder(reader, bufferSize).toString();
    }

    static StringBuilder asStringBuilder(Object relativeTo, String name) throws IOException {
        return asStringBuilder(relativeTo.getClass(), name);
    }

    static StringBuilder asStringBuilder(Class<?> relativeTo, String name) throws IOException {
        return asStringBuilder(asInputStream(relativeTo, name));
    }

    static StringBuilder asStringBuilder(InputStream in) throws IOException {
        return asStringBuilder(in, StandardCharsets.UTF_8);
    }

    static StringBuilder asStringBuilder(InputStream in, Charset encoding) throws IOException {
        return asStringBuilder(in, encoding, 4096);
    }

    static StringBuilder asStringBuilder(InputStream in, Charset encoding, int bufferSize) throws IOException {
        int available = in.available();
        StringBuilder builder;
        if (available == 0) //not sure how many bytes are there in the stream
            builder = new StringBuilder(bufferSize);
        else
            builder = new StringBuilder(available);
        InputStreamReader reader = new InputStreamReader(in, encoding);
        char[] buffer = new char[bufferSize];
        int n;
        while (-1 != (n = reader.read(buffer))) {
            builder.append(buffer, 0, n);
        }
        return builder;
    }

    static StringBuilder asStringBuilder(Reader reader) throws IOException {
        return asStringBuilder(reader, 4096);
    }

    static StringBuilder asStringBuilder(Reader reader, int bufferSize) throws IOException {
        StringBuilder builder = new StringBuilder(bufferSize);
        char[] buffer = new char[bufferSize];
        int n;
        while (-1 != (n = reader.read(buffer))) {
            builder.append(buffer, 0, n);
        }
        return builder;
    }

    static Map<String, String> asMapFromProperties(Object relativeTo, String name) throws IOException {
        return asMapFromProperties(relativeTo.getClass(), name);
    }

    static Map<String, String> asMapFromProperties(Class<?> relativeTo, String name) throws IOException {
        return asMapFromProperties(asInputStream(relativeTo, name));
    }

    static Map<String, String> asMapFromProperties(String name) throws IOException {
        return asMapFromProperties(asInputStream(name));
    }

    static Map<String, String> asMapFromProperties(InputStream in) throws IOException {
        return asProperties(in).entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
    }

    static Map<String, String> asMapFromProperties(Reader reader) throws IOException {
        return asProperties(reader).entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
    }

    static Properties asProperties(Object relativeTo, String name) throws IOException {
        return asProperties(relativeTo.getClass(), name);
    }

    static Properties asProperties(Class<?> relativeTo, String name) throws IOException {
        return asProperties(asInputStream(relativeTo, name));
    }

    static Properties asProperties(String name) throws IOException {
        return asProperties(asInputStream(name));
    }

    static Properties asProperties(InputStream in) throws IOException {
        try (in) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        }
    }

    static Properties asProperties(Reader reader) throws IOException {
        try (reader) {
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        }
    }

}
