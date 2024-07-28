package com.vasnatech.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedTetraOperator<T> extends CheckedTetraFunction<T, T, T, T, T> {

    @Override
    default TetraOperator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    @Override
    default TetraOperator<T> unchecked(Function<Exception, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T> CheckedTetraOperator<T> checked(TetraOperator<T> operator) {
        return operator::apply;
    }

    static <T> TetraOperator<T> unchecked(CheckedTetraOperator<T> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T> TetraOperator<T>  unchecked(CheckedTetraOperator<T> checked, Function<Exception, T> exceptionHandler) {
        return (first, second, third, fourth) -> {
            try {
                return checked.apply(first, second, third, fourth);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
