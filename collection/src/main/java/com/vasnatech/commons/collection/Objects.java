package com.vasnatech.commons.collection;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Objects {

    private Objects() {}

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object deepMerge(Object value, Object newValue) {
        if (value == null)
            return newValue;
        if (newValue == null)
            return value;
        if (value instanceof Map && newValue instanceof Map)
            return Maps.deepMerge((Map) value, (Map) newValue);
        if (value instanceof Collection && newValue instanceof Collection)
            return Collections.deepMerge((Collection) value, (Collection) newValue);
        return newValue;
    }

    public static Object deepCopy(Object value) {
        if (value instanceof Map)
            return Maps.deepCopy((Map<?, ?>) value);
        if (value instanceof List)
            return Lists.deepCopy((List<?>) value);
        if (value instanceof Set)
            return Sets.deepCopy((Set<?>) value);
        return value;
    }
}
