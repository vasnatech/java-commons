package com.vasnatech.commons.function;

import java.util.function.BinaryOperator;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedBiOperator<T> extends CheckedBiFunction<T, T, T> {

    @Override
    default BinaryOperator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    @Override
    default BinaryOperator<T> unchecked(Function<Exception, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T> CheckedBiOperator<T> checked(BinaryOperator<T> operator) {
        return operator::apply;
    }

    static <T> BinaryOperator<T> unchecked(CheckedBiOperator<T> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T> BinaryOperator<T>  unchecked(CheckedBiOperator<T> checked, Function<Exception, T> exceptionHandler) {
        return (first, second) -> {
            try {
                return checked.apply(first, second);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
