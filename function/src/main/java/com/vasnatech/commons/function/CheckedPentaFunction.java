package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> {

    R apply(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) throws Exception;

    default PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked(Function<Exception, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <R$> CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R$> andThen(CheckedFunction<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth, fifth) -> after.apply(apply(first, second, third, fourth, fifth));
    }

    default <R$> CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R$> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth, fifth) -> after.apply(apply(first, second, third, fourth, fifth));
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R> CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> checked(PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> function) {
        return function::apply;
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked(CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked(CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> checked, Function<Exception, R> exceptionHandler) {
        return (first, second, third, fourth, fifth) -> {
            try {
                return checked.apply(first, second, third, fourth, fifth);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
