package com.vasnatech.commons.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMappers<T> {

    private final Map<Class<? extends Throwable>, ExceptionMapper<?, T>> mappers;

    public ExceptionMappers() {
        this.mappers = new HashMap<>();
    }

    public <E extends Throwable> void put(Class<E> exceptionType, ExceptionMapper<E, T> exceptionMapper) {
        mappers.put(exceptionType, exceptionMapper);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public T map(Throwable ex) {
        Throwable parent = null;
        Throwable current = ex;
        while (current != null && parent != current) {
            Class<? extends Throwable> currentClass = current.getClass();
            if (mappers.containsKey(currentClass)) {
                ExceptionMapper exceptionMapper = mappers.get(currentClass);
                return (T) exceptionMapper.map(current);
            }
            for (Map.Entry<Class<? extends Throwable>, ExceptionMapper<?, T>> entry : mappers.entrySet()) {
                if (entry.getKey().isAssignableFrom(currentClass)) {
                    ExceptionMapper exceptionMapper = entry.getValue();
                    return (T) exceptionMapper.map(current);
                }
            }
            parent = current;
            current = current.getCause();
        }
        return null;
    }
}
