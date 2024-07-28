package com.vasnatech.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedOperator<T> extends CheckedFunction<T, T> {

    @Override
    default Operator<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    @Override
    default Operator<T> unchecked(Function<Exception, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T> CheckedOperator<T> checked(Operator<T> operator) {
        return operator::apply;
    }

    static <T> Operator<T> unchecked(CheckedOperator<T> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T> Operator<T>  unchecked(CheckedOperator<T> checked, Function<Exception, T> exceptionHandler) {
        return (first) -> {
            try {
                return checked.apply(first);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
