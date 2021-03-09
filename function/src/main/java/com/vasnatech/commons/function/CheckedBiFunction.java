package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedBiFunction<T, U, R> {

    R apply(T t, U u) throws Exception;

    default BiFunction<T, U, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default BiFunction<T, U, R> unchecked(Function<Exception, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <V> CheckedBiFunction<T, U, V> andThen(CheckedFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (t, u) -> after.apply(apply(t, u));
    }

    default <V> CheckedBiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (t, u) -> after.apply(apply(t, u));
    }

    static <T, U, R> CheckedBiFunction<T, U, R> checked(BiFunction<T, U, R> function) {
        return function::apply;
    }

    static <T, U, R> BiFunction<T, U, R> unchecked(CheckedBiFunction<T, U, R> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T, U, R> BiFunction<T, U, R> unchecked(CheckedBiFunction<T, U, R> checked, Function<Exception, R> exceptionHandler) {
        return (t, u) -> {
            try {
                return checked.apply(t, u);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
