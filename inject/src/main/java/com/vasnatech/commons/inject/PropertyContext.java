package com.vasnatech.commons.inject;

import com.vasnatech.commons.function.CheckedSupplier;
import com.vasnatech.commons.json.Json;
import com.vasnatech.commons.json.jackson.JsonJackson;
import com.vasnatech.commons.mapper.MapperContext;
import com.vasnatech.commons.mapper.MapperContexts;
import com.vasnatech.commons.properties.Properties;
import com.vasnatech.commons.properties.jackson.PropertiesJackson;
import com.vasnatech.commons.resource.Resources;
import com.vasnatech.commons.serialize.Decoder;
import com.vasnatech.commons.yaml.Yaml;
import com.vasnatech.commons.yaml.jackson.YamlJackson;

import java.io.InputStream;
import java.util.Map;

public class PropertyContext {

    private static PropertyContext INSTANCE;

    public static PropertyContext getPropertyContext() {
        return INSTANCE;
    }


    public static void init() {
        init(MapperContexts.compound(MapperContexts.javaPrimitive(), MapperContexts.javaTime()));
    }

    public static void init(MapperContext mapperContext) {
        if (INSTANCE != null) return;

        String decoderType = null;
        InputStream in;
        in = Resources.asInputStream("application.yaml");
        if (in != null) {
            decoderType = "yaml";
        } else {
            in = Resources.asInputStream("application.json");
            if (in != null) {
                decoderType = "json";
            } else {
                in = Resources.asInputStream("application.properties");
                if (in != null) {
                    decoderType = "properties";
                }
            }
        }
        final Decoder decoder = findDecoder(decoderType);
        final InputStream propertiesIn = in;
        Map<String, ?> properties = (in == null || decoder == null) ? Map.of() : CheckedSupplier.get(() -> decoder.fromInputStream(propertiesIn, Map.class, String.class, Object.class));
        INSTANCE = new PropertyContext(mapperContext, properties);
    }

    static Decoder findDecoder(String type) {
        if (type == null) return null;
        return switch (type) {
            case "yaml" -> {
                YamlJackson.init();
                yield Yaml.decoder();
            }
            case "json" -> {
                JsonJackson.init();
                yield Json.decoder();
            }
            case "properties" -> {
                PropertiesJackson.init();
                yield Properties.decoder();
            }
            default -> null;
        };
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getClassOf(T[] array) {
        return (Class<T>) array.getClass().getComponentType();
    }


    Map<String, ?> properties;
    MapperContext mapperContext;

    PropertyContext(MapperContext mapperContext, Map<String, ?> properties) {
        this.mapperContext = mapperContext;
        this.properties = properties;
    }

    private Object getProperty(String path) {
        if (path == null) return null;
        String[] keys = path.split("\\.");
        Object current = properties;
        for (String key : keys) {
            if (current == null) {
                return null;
            }
            if (current instanceof Map<?,?> map) {
                current = map.get(key);
            } else {
                return null;
            }
        }
        return current;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String path, Class<T> clazz) {
        Object value = getProperty(path);
        if (value == null) {
            return null;
        }
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        return mapperContext.map(value);
    }

    @SafeVarargs
    public final <T> T getProperty(String path, T... reified) {
        return getProperty(path, getClassOf(reified));
    }
}
