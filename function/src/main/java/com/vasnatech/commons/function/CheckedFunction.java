package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<T, R> {

    R apply(T t) throws Exception;

    default Function<T, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default Function<T, R> unchecked(Function<Exception, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <V> CheckedFunction<V, R> compose(CheckedFunction<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return v -> apply(before.apply(v));
    }

    default <V> CheckedFunction<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return v -> apply(before.apply(v));
    }

    default <V> CheckedFunction<T, V> andThen(CheckedFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return t -> after.apply(apply(t));
    }

    default <V> CheckedFunction<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return t -> after.apply(apply(t));
    }

    static <T, R> Function<T, R> unchecked(CheckedFunction<T, R> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T, R> Function<T, R> unchecked(CheckedFunction<T, R> checked, Function<Exception, R> exceptionHandler) {
        return t -> {
            try {
                return checked.apply(t);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }

    static <T, R> CheckedFunction<T, R> checked(Function<T, R> function) {
        return function::apply;
    }

    static <T> CheckedFunction<T, T> identity() {
        return t -> t;
    }
}
