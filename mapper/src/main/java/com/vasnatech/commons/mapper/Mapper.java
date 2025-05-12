package com.vasnatech.commons.mapper;

import java.util.function.Function;

@FunctionalInterface
public interface Mapper<S, T> extends Function<S, T> {

    T map(S source);

    @Override
    default T apply(S source) {
        return map(source);
    }
}
