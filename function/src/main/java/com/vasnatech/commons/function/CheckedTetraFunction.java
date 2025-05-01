package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R, E extends Throwable> {

    R apply(FIRST first, SECOND second, THIRD third, FOURTH fourth) throws E;

    default TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked(Function<E, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <R$> CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R$, E> andThen(CheckedFunction<? super R, ? extends R$, ? extends E> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth) -> after.apply(apply(first, second, third, fourth));
    }

    default <R$> CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R$, E> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth) -> after.apply(apply(first, second, third, fourth));
    }

    static <FIRST, SECOND, THIRD, FOURTH, R, E extends Throwable> CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R, E> checked(TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> function) {
        return function::apply;
    }

    static <FIRST, SECOND, THIRD, FOURTH, R, E extends Throwable> TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked(CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, SECOND, THIRD, FOURTH, R, E extends Throwable> TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked(CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R, E> checked, Function<E, R> exceptionHandler) {
        return (first, second, third, fourth) -> apply(first, second, third, fourth, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH, R, E extends Throwable> R apply(FIRST first, SECOND second, THIRD third, FOURTH fourth, CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R, E> checked, Function<E, R> exceptionHandler) {
        try {
            return checked.apply(first, second, third, fourth);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <FIRST, SECOND, THIRD, FOURTH, R, E extends Throwable> R apply(FIRST first, SECOND second, THIRD third, FOURTH fourth, CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R, E> checked) {
        return apply(first, second, third, fourth, checked, ExceptionHandler.asFunction());
    }
}
