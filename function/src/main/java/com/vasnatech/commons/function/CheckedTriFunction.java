package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedTriFunction<FIRST, SECOND, THIRD, R, E extends Throwable> {

    R apply(FIRST first, SECOND second, THIRD third) throws E;

    default TriFunction<FIRST, SECOND, THIRD, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default TriFunction<FIRST, SECOND, THIRD, R> unchecked(Function<E, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <W> CheckedTriFunction<FIRST, SECOND, THIRD, W, E> andThen(CheckedFunction<? super R, ? extends W, ? extends E> after) {
        Objects.requireNonNull(after);
        return (first, second, third) -> after.apply(apply(first, second, third));
    }

    default <R$> CheckedTriFunction<FIRST, SECOND, THIRD, R$, E> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third) -> after.apply(apply(first, second, third));
    }

    static <FIRST, SECOND, THIRD, R, E extends Throwable> CheckedTriFunction<FIRST, SECOND, THIRD, R, E> checked(TriFunction<FIRST, SECOND, THIRD, R> function) {
        return function::apply;
    }

    static <FIRST, SECOND, THIRD, R, E extends Throwable> TriFunction<FIRST, SECOND, THIRD, R> unchecked(CheckedTriFunction<FIRST, SECOND, THIRD, R, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, SECOND, THIRD, R, E extends Throwable> TriFunction<FIRST, SECOND, THIRD, R> unchecked(CheckedTriFunction<FIRST, SECOND, THIRD, R, E> checked, Function<E, R> exceptionHandler) {
        return (first, second, third) -> apply(first, second, third, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, R, E extends Throwable> R apply(FIRST first, SECOND second, THIRD third, CheckedTriFunction<FIRST, SECOND, THIRD, R, E> checked, Function<E, R> exceptionHandler) {
        try {
            return checked.apply(first, second, third);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <FIRST, SECOND, THIRD, R, E extends Throwable> R apply(FIRST first, SECOND second, THIRD third, CheckedTriFunction<FIRST, SECOND, THIRD, R, E> checked) {
        return apply(first, second, third, checked, ExceptionHandler.asFunction());
    }
}
