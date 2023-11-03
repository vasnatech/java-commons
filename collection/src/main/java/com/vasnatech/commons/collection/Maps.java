package com.vasnatech.commons.collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Maps {

    private Maps() {}

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> concat(Map<? extends K, ? extends V>... maps) {
        return Stream.of(maps)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> x));
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> of(Object... objects) {
        int length = objects.length / 2 * 2;
        HashMap<K, V> map = new HashMap<>(length / 2);
        for (int index = 0; index < length; index += 2)
            map.put((K)objects[index], (V)objects[index + 1]);
        return java.util.Collections.unmodifiableMap(map);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> ofNotNull(Object... objects) {
        int length = objects.length / 2 * 2;
        HashMap<K, V> map = new HashMap<>(length / 2);
        for (int index = 0; index < length; index += 2) {
            if (objects[index] == null || objects[index + 1] == null)
                continue;
            map.put((K)objects[index], (V)objects[index + 1]);
        }
        return java.util.Collections.unmodifiableMap(map);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> ofNotNullKey(Object... objects) {
        int length = objects.length / 2 * 2;
        HashMap<K, V> map = new HashMap<>(length / 2);
        for (int index = 0; index < length; index += 2) {
            if (objects[index] == null)
                continue;
            map.put((K)objects[index], (V)objects[index + 1]);
        }
        return java.util.Collections.unmodifiableMap(map);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> ofNotNullValue(Object... objects) {
        int length = objects.length / 2 * 2;
        HashMap<K, V> map = new HashMap<>(length / 2);
        for (int index = 0; index < length; index += 2) {
            if (objects[index + 1] == null)
                continue;
            map.put((K)objects[index], (V)objects[index + 1]);
        }
        return java.util.Collections.unmodifiableMap(map);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> of(List<?> objects) {
        int length = objects.size() / 2 * 2;
        HashMap<K, V> map = new HashMap<>(length / 2);
        for (int index = 0; index < length; index += 2)
            map.put((K)objects.get(index), (V)objects.get(index + 1));
        return java.util.Collections.unmodifiableMap(map);
    }

    @SuppressWarnings({"unchecked"})
    public static <K, V> Map<K, V> deepMerge(Map<K, V> value, Map<K, V> newValue) {
        if (value == null)
            return newValue;
        if (newValue == null)
            return value;
        value.putAll(newValue.keySet().stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                key -> (V) Objects.deepMerge(value.get(key), newValue.get(key))
                        )
                )
        );
        return value;
    }

    public static Map<?, ?> deepCopy(Map<?, ?> source) {
        return source.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> Objects.deepCopy(entry.getValue())
                        )
                );
    }

    public static <K1, K2, V1, V2> Map<K2, V2> map(Map<? extends K1, ? extends V1> map, Function<K1, K2> keyMapper, Function<V1, V2> valueMapper) {
        return map.entrySet().stream().collect(Collectors.toMap(e -> keyMapper.apply(e.getKey()), e -> valueMapper.apply(e.getValue())));
    }

    public static <K1, K2, V> Map<K2, V> mapKeys(Map<? extends K1, ? extends V> map, Function<K1, K2> keyMapper) {
        return map(map, keyMapper, Function.identity());
    }

    public static <K, V1, V2> Map<K, V2> mapValues(Map<? extends K, ? extends V1> map, Function<V1, V2> valueMapper) {
        return map(map, Function.identity(), valueMapper);
    }
}
