package com.vasnatech.commons.yaml;

import com.vasnatech.commons.resource.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface YamlResources {

    static Map<String, ?> asMap(Object relativeTo, String name) throws IOException {
        return Yaml.decoder().fromInputStreamToMap(Resources.asInputStream(relativeTo, name));
    }
    static Map<String, ?> asMap(Class<?> relativeTo, String name) throws IOException {
        return Yaml.decoder().fromInputStreamToMap(Resources.asInputStream(relativeTo, name));
    }
    static Map<String, ?> asMap(InputStream inputStream) throws IOException {
        return Yaml.decoder().fromInputStreamToMap(inputStream);
    }

    static <T> T asObject(Object relativeTo, String name, Class<T> type) throws IOException {
        return Yaml.decoder().fromInputStream(Resources.asInputStream(relativeTo, name), type);
    }
    static <T> T asObject(Class<?> relativeTo, String name, Class<T> type) throws IOException {
        return Yaml.decoder().fromInputStream(Resources.asInputStream(relativeTo, name), type);
    }
    static <T> T asObject(InputStream inputStream, Class<T> type) throws IOException {
        return Yaml.decoder().fromInputStream(inputStream, type);
    }
}
