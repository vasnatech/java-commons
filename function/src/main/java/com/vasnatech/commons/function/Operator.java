package com.vasnatech.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface Operator<T> extends Function<T, T> {

    Operator<?> IDENTITY = value -> value;

    @SuppressWarnings("unchecked")
    static <T> Operator<T> identity() {
        return (Operator<T>) IDENTITY;
    }
}
