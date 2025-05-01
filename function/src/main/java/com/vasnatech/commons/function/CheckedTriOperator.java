package com.vasnatech.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedTriOperator<T, E extends Throwable> extends CheckedTriFunction<T, T, T, T, E> {

    @Override
    default TriOperator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    @Override
    default TriOperator<T> unchecked(Function<E, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T, E extends Throwable> CheckedTriOperator<T, E> checked(TriOperator<T> operator) {
        return operator::apply;
    }

    static <T, E extends Throwable> TriOperator<T> unchecked(CheckedTriOperator<T, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T, E extends Throwable> TriOperator<T> unchecked(CheckedTriOperator<T, E> checked, Function<E, T> exceptionHandler) {
        return (first, second, third) -> apply(first, second, third, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <T, E extends Throwable> T apply(T first, T second, T third, CheckedTriOperator<T, E> checked, Function<E, T> exceptionHandler) {
        try {
            return checked.apply(first, second, third);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <T, E extends Throwable> T apply(T first, T second, T third, CheckedTriOperator<T, E> checked) {
        return apply(first, second, third, checked, ExceptionHandler.asFunction());
    }
}
