package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<FIRST, R> {

    R apply(FIRST first) throws Exception;

    default Function<FIRST, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default Function<FIRST, R> unchecked(Function<Exception, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <R$> CheckedFunction<R$, R> compose(CheckedFunction<? super R$, ? extends FIRST> before) {
        Objects.requireNonNull(before);
        return v -> apply(before.apply(v));
    }

    default <R$> CheckedFunction<R$, R> compose(Function<? super R$, ? extends FIRST> before) {
        Objects.requireNonNull(before);
        return v -> apply(before.apply(v));
    }

    default <R$> CheckedFunction<FIRST, R$> andThen(CheckedFunction<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return first -> after.apply(apply(first));
    }

    default <R$> CheckedFunction<FIRST, R$> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return first -> after.apply(apply(first));
    }

    static <FIRST, R> Function<FIRST, R> unchecked(CheckedFunction<FIRST, R> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, R> Function<FIRST, R> unchecked(CheckedFunction<FIRST, R> checked, Function<Exception, R> exceptionHandler) {
        return first -> {
            try {
                return checked.apply(first);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }

    static <FIRST, R> CheckedFunction<FIRST, R> checked(Function<FIRST, R> function) {
        return function::apply;
    }

    static <FIRST> CheckedFunction<FIRST, FIRST> identity() {
        return t -> t;
    }
}
