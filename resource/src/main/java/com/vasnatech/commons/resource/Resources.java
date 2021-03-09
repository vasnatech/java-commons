package com.vasnatech.commons.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    static String asString(Object relativeTo, String name) throws IOException {
        return asString(relativeTo.getClass(), name);
    }

    static String asString(Class<?> relativeTo, String name) throws IOException {
        return asString(asInputStream(relativeTo, name));
    }

    static String asString(String name) throws IOException {
        return asString(asInputStream(name));
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

}
