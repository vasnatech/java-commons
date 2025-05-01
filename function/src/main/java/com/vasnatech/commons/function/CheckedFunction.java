package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<FIRST, R, E extends Throwable> {

    R apply(FIRST first) throws E;

    default Function<FIRST, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default <R$> CheckedFunction<R$, R, E> compose(CheckedFunction<? super R$, ? extends FIRST, ? extends E> before) {
        Objects.requireNonNull(before);
        return v -> apply(before.apply(v));
    }

    default <R$> CheckedFunction<R$, R, E> compose(Function<? super R$, ? extends FIRST> before) {
        Objects.requireNonNull(before);
        return v -> apply(before.apply(v));
    }

    default <R$> CheckedFunction<FIRST, R$, E> andThen(CheckedFunction<? super R, ? extends R$, ? extends E> after) {
        Objects.requireNonNull(after);
        return first -> after.apply(apply(first));
    }

    default <R$> CheckedFunction<FIRST, R$, E> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return first -> after.apply(apply(first));
    }

    static <FIRST, R, E extends Throwable> Function<FIRST, R> unchecked(CheckedFunction<FIRST, R, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, R, E extends Throwable> CheckedFunction<FIRST, R, E> checked(Function<FIRST, R> function) {
        return function::apply;
    }

    static <FIRST, R, E extends Throwable> Function<FIRST, R> unchecked(CheckedFunction<FIRST, R, E> checked, Function<E, R> exceptionHandler) {
        return first -> apply(first, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, R, E extends Throwable> R apply(FIRST first, CheckedFunction<FIRST, R, E> checked, Function<E, R> exceptionHandler) {
        try {
            return checked.apply(first);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <FIRST, R, E extends Throwable> R apply(FIRST first, CheckedFunction<FIRST, R, E> checked) {
        return apply(first, checked, ExceptionHandler.asFunction());
    }

    static <FIRST, E extends Throwable> CheckedFunction<FIRST, FIRST, E> identity() {
        return t -> t;
    }
}
