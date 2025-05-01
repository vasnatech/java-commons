package com.vasnatech.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedOperator<T, E extends Throwable> extends CheckedFunction<T, T, E> {

    @Override
    default Operator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    static <T, E extends Throwable> CheckedOperator<T, E> checked(Operator<T> operator) {
        return operator::apply;
    }

    static <T, E extends Throwable> Operator<T> unchecked(CheckedOperator<T, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T, E extends Throwable> Operator<T> unchecked(CheckedOperator<T, E> checked, Function<E, T> exceptionHandler) {
        return (t) -> apply(t, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <T, E extends Throwable> T apply(T t, CheckedOperator<T, E> checked, Function<E, T> exceptionHandler) {
        try {
            return checked.apply(t);
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <T, E extends Throwable> T apply(T t, CheckedOperator<T, E> checked) {
        return apply(t, checked, ExceptionHandler.asFunction());
    }
}
