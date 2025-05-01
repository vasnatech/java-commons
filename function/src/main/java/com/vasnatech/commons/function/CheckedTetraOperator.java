package com.vasnatech.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedTetraOperator<T, E extends Throwable> extends CheckedTetraFunction<T, T, T, T, T, E> {

    @Override
    default TetraOperator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    @Override
    default TetraOperator<T> unchecked(Function<E, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T, E extends Throwable> CheckedTetraOperator<T, E> checked(TetraOperator<T> operator) {
        return operator::apply;
    }

    static <T, E extends Throwable> TetraOperator<T> unchecked(CheckedTetraOperator<T, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T, E extends Throwable> TetraOperator<T> unchecked(CheckedTetraOperator<T, E> checked, Function<E, T> exceptionHandler) {
        return (first, second, third, fourth) -> apply(first, second, third, fourth, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <T, E extends Throwable> T apply(T first, T second, T third, T fourth, CheckedTetraOperator<T, E> checked, Function<E, T> exceptionHandler) {
        try {
            return checked.apply(first, second, third, fourth);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <T, E extends Throwable> T apply(T first, T second, T third, T fourth, CheckedTetraOperator<T, E> checked) {
        return apply(first, second, third, fourth, checked, ExceptionHandler.asFunction());
    }
}
