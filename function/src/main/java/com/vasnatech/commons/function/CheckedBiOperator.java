package com.vasnatech.commons.function;

import java.util.function.BinaryOperator;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedBiOperator<T, E extends Throwable> extends CheckedBiFunction<T, T, T, E> {

    @Override
    default BinaryOperator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    @Override
    default BinaryOperator<T> unchecked(Function<E, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T, E extends Throwable> CheckedBiOperator<T, E> checked(BinaryOperator<T> operator) {
        return operator::apply;
    }

    static <T, E extends Throwable> BinaryOperator<T> unchecked(CheckedBiOperator<T, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T, E extends Throwable> BinaryOperator<T>  unchecked(CheckedBiOperator<T, E> checked, Function<E, T> exceptionHandler) {
        return (first, second) -> apply(first, second, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <T, E extends Throwable> T apply(T first, T second, CheckedBiOperator<T, E> checked, Function<E, T> exceptionHandler) {
        try {
            return checked.apply(first, second);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <T, E extends Throwable> T apply(T first, T second, CheckedBiOperator<T, E> checked) {
        return apply(first, second, checked, ExceptionHandler.asFunction());
    }
}
