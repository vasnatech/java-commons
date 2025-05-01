package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E extends Throwable> {

    R apply(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) throws E;

    default PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked(Function<E, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <R$> CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R$, E> andThen(CheckedFunction<? super R, ? extends R$, ? extends E> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth, fifth) -> after.apply(apply(first, second, third, fourth, fifth));
    }

    default <R$> CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R$, E> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth, fifth) -> after.apply(apply(first, second, third, fourth, fifth));
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E extends Throwable> CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E> checked(PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> function) {
        return function::apply;
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E extends Throwable> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked(CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E extends Throwable> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked(CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E> checked, Function<E, R> exceptionHandler) {
        return (first, second, third, fourth, fifth) -> apply(first, second, third, fourth, fifth, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E extends Throwable> R apply(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth, CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E> checked, Function<E, R> exceptionHandler) {
        try {
            return checked.apply(first, second, third, fourth, fifth);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E extends Throwable> R apply(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth, CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R, E> checked) {
        return apply(first, second, third, fourth, fifth, checked, ExceptionHandler.asFunction());
    }
}
