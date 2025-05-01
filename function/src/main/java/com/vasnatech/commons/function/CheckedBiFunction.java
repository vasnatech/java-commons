package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedBiFunction<FIRST, SECOND, R, E extends Throwable> {

    R apply(FIRST first, SECOND second) throws E;

    default BiFunction<FIRST, SECOND, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default BiFunction<FIRST, SECOND, R> unchecked(Function<E, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <R$> CheckedBiFunction<FIRST, SECOND, R$, E> andThen(CheckedFunction<? super R, ? extends R$, ? extends E> after) {
        Objects.requireNonNull(after);
        return (first, second) -> after.apply(apply(first, second));
    }

    default <R$> CheckedBiFunction<FIRST, SECOND, R$, E> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second) -> after.apply(apply(first, second));
    }

    static <FIRST, SECOND, R, E extends Throwable> CheckedBiFunction<FIRST, SECOND, R, E> checked(BiFunction<FIRST, SECOND, R> function) {
        return function::apply;
    }

    static <FIRST, SECOND, R, E extends Throwable> BiFunction<FIRST, SECOND, R> unchecked(CheckedBiFunction<FIRST, SECOND, R, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, SECOND, R, E extends Throwable> BiFunction<FIRST, SECOND, R> unchecked(CheckedBiFunction<FIRST, SECOND, R, E> checked, Function<E, R> exceptionHandler) {
        return (first, second) -> apply(first, second, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, R, E extends Throwable> R apply(FIRST first, SECOND second, CheckedBiFunction<FIRST, SECOND, R, E> checked, Function<E, R> exceptionHandler) {
        try {
            return checked.apply(first, second);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <FIRST, SECOND, R, E extends Throwable> R apply(FIRST first, SECOND second, CheckedBiFunction<FIRST, SECOND, R, E> checked) {
        return apply(first, second, checked, ExceptionHandler.asFunction());
    }
}
