package com.vasnatech.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedTriOperator<T> extends CheckedTriFunction<T, T, T, T> {

    @Override
    default TriOperator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    @Override
    default TriOperator<T> unchecked(Function<Exception, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T> CheckedTriOperator<T> checked(TriOperator<T> operator) {
        return operator::apply;
    }

    static <T> TriOperator<T> unchecked(CheckedTriOperator<T> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T> TriOperator<T>  unchecked(CheckedTriOperator<T> checked, Function<Exception, T> exceptionHandler) {
        return (first, second, third) -> {
            try {
                return checked.apply(first, second, third);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
