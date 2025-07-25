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
import com.vasnatech.commons.text.token.Token;
import com.vasnatech.commons.text.token.Tokenizer;
import com.vasnatech.commons.yaml.Yaml;
import com.vasnatech.commons.yaml.jackson.YamlJackson;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.SequencedSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyContext {

    private static PropertyContext INSTANCE;

    public static PropertyContext getPropertyContext() {
        return INSTANCE;
    }


    public static void init() {
        init(MapperContexts.compound(MapperContexts.javaPrimitive(), MapperContexts.javaTime()));
    }

    public static void init(MapperContext mapperContext) {
        init(
                mapperContext,
                Stream.of(
                        "application.yaml",
                        "application.json",
                        "application.properties"
                ).collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

    public static void init(MapperContext mapperContext, SequencedSet<String> propertyFiles) {
        if (INSTANCE != null) return;

        Map<String, Object> allProperties = new LinkedHashMap<>();
        for (String propertyFile : propertyFiles) {
            InputStream in = Resources.asInputStream(propertyFile);
            if (in == null) {
                continue;
            }
            Decoder decoder = Optional.of(propertyFile)
                    .map(PropertyContext::findFileExtension)
                    .map(PropertyContext::findDecoder)
                    .orElse(null);
            if (decoder == null) {
                continue;
            }
            Map<String, Object> properties = CheckedSupplier.get(() -> decoder.fromInputStream(in, Map.class, String.class, Object.class));
            allProperties.putAll(properties);
        }

        INSTANCE = new PropertyContext(mapperContext, allProperties);
    }

    static String findFileExtension(String fileName) {
        int idx = fileName.lastIndexOf('.');
        if (idx >= 0) {
            return fileName.substring(idx + 1);
        }
        return null;
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
    Tokenizer<Boolean> valueTokenizer;

    PropertyContext(MapperContext mapperContext, Map<String, ?> properties) {
        this.mapperContext = mapperContext;
        this.properties = properties;
        valueTokenizer = new Tokenizer<>(new Token<>("$(", Boolean.TRUE), new Token<>(")", Boolean.FALSE));
    }

    private Object getProperty(String path) {
        if (path == null) return null;
        Object value = System.getenv(path);
        if (value != null) {
            return evaluateValue(value);
        }
        value = System.getProperty(path);
        if (value != null) {
            return evaluateValue(value);
        }
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
        return evaluateValue(current);
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

    private Object evaluateValue(Object value) {
        if (value instanceof String stringValue) {
            Iterator<Token<Boolean>> iterator = valueTokenizer.tokenize(stringValue);
            StringBuilder newValue = new StringBuilder(stringValue.length());
            boolean reference = false;
            while (iterator.hasNext()) {
                Token<Boolean> token = iterator.next();
                if (token.getValue() == null) {
                    if (reference) {
                        Object referenceValue = getProperty(token.getMatch());
                        newValue.append(referenceValue == null ? "" : referenceValue);
                    } else {
                        newValue.append(token.getMatch());
                    }
                } else {
                    reference = token.getValue();
                }
            }
            return newValue.toString();
        }
        return value;
    }
}
