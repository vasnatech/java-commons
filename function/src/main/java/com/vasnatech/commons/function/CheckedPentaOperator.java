package com.vasnatech.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedPentaOperator<T> extends CheckedPentaFunction<T, T, T, T, T, T> {

    @Override
    default PentaOperator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    @Override
    default PentaOperator<T> unchecked(Function<Exception, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T> CheckedPentaOperator<T> checked(PentaOperator<T> operator) {
        return operator::apply;
    }

    static <T> PentaOperator<T> unchecked(CheckedPentaOperator<T> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T> PentaOperator<T>  unchecked(CheckedPentaOperator<T> checked, Function<Exception, T> exceptionHandler) {
        return (first, second, third, fourth, fifth) -> {
            try {
                return checked.apply(first, second, third, fourth, fifth);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
