package com.vasnatech.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedPentaOperator<T, E extends Throwable> extends CheckedPentaFunction<T, T, T, T, T, T, E> {

    @Override
    default PentaOperator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    @Override
    default PentaOperator<T> unchecked(Function<E, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T, E extends Throwable> CheckedPentaOperator<T, E> checked(PentaOperator<T> operator) {
        return operator::apply;
    }

    static <T, E extends Throwable> PentaOperator<T> unchecked(CheckedPentaOperator<T, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T, E extends Throwable> PentaOperator<T>  unchecked(CheckedPentaOperator<T, E> checked, Function<E, T> exceptionHandler) {
        return (first, second, third, fourth, fifth) -> apply(first, second, third, fourth, fifth, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <T, E extends Throwable> T apply(T first, T second, T third, T fourth, T fifth, CheckedPentaOperator<T, E> checked, Function<E, T> exceptionHandler) {
        try {
            return checked.apply(first, second, third, fourth, fifth);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <T, E extends Throwable> T apply(T first, T second, T third, T fourth, T fifth, CheckedPentaOperator<T, E> checked) {
        return apply(first, second, third, fourth, fifth, checked, ExceptionHandler.asFunction());
    }
}
