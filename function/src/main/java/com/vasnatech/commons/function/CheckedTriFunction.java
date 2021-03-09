package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedTriFunction<T, U, V, R> {

    R apply(T t, U u, V v) throws Exception;

    default TriFunction<T, U, V, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default TriFunction<T, U, V, R> unchecked(Function<Exception, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <W> CheckedTriFunction<T, U, V, W> andThen(CheckedFunction<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (t, u, v) -> after.apply(apply(t, u, v));
    }

    default <W> CheckedTriFunction<T, U, V, W> andThen(Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (t, u, v) -> after.apply(apply(t, u, v));
    }

    static <T, U, V, R> CheckedTriFunction<T, U, V, R> checked(TriFunction<T, U, V, R> function) {
        return function::apply;
    }

    static <T, U, V, R> TriFunction<T, U, V, R> unchecked(CheckedTriFunction<T, U, V, R> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T, U, V, R> TriFunction<T, U, V, R> unchecked(CheckedTriFunction<T, U, V, R> checked, Function<Exception, R> exceptionHandler) {
        return (t, u, v) -> {
            try {
                return checked.apply(t, u, v);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
