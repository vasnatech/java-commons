package com.vasnatech.commons.json;

import com.vasnatech.commons.resource.Resources;

import java.io.IOException;
import java.util.Map;

public interface JsonResources {

    static String asString(Object relativeTo, String name) throws IOException {
        return Resources.asString(relativeTo, name);
    }

    static String asString(Class<?> relativeTo, String name) throws IOException {
        return Resources.asString(relativeTo, name);
    }

    static Map<String, ?> asMap(Object relativeTo, String name) throws IOException {
        return Json.decoder().fromInputStreamToMap(Resources.asInputStream(relativeTo, name));
    }
    static Map<String, ?> asMap(Class<?> relativeTo, String name) throws IOException {
        return Json.decoder().fromInputStreamToMap(Resources.asInputStream(relativeTo, name));
    }

    static <T> T asObject(Object relativeTo, String name, Class<T> type) throws IOException {
        return Json.decoder().fromInputStream(Resources.asInputStream(relativeTo, name), type);
    }
    static <T> T asObject(Class<?> relativeTo, String name, Class<T> type) throws IOException {
        return Json.decoder().fromInputStream(Resources.asInputStream(relativeTo, name), type);
    }
}
