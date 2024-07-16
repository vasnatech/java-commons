package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R> {

    R apply(FIRST first, SECOND second, THIRD third, FOURTH fourth) throws Exception;

    default TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked(Function<Exception, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <R$> CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R$> andThen(CheckedFunction<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth) -> after.apply(apply(first, second, third, fourth));
    }

    default <R$> CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R$> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth) -> after.apply(apply(first, second, third, fourth));
    }

    static <FIRST, SECOND, THIRD, FOURTH, R> CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R> checked(TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> function) {
        return function::apply;
    }

    static <FIRST, SECOND, THIRD, FOURTH, R> TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked(CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, SECOND, THIRD, FOURTH, R> TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked(CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R> checked, Function<Exception, R> exceptionHandler) {
        return (first, second, third, fourth) -> {
            try {
                return checked.apply(first, second, third, fourth);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
