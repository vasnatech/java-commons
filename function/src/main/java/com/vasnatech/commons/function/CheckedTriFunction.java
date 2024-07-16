package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedTriFunction<FIRST, SECOND, THIRD, R> {

    R apply(FIRST first, SECOND second, THIRD third) throws Exception;

    default TriFunction<FIRST, SECOND, THIRD, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default TriFunction<FIRST, SECOND, THIRD, R> unchecked(Function<Exception, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <W> CheckedTriFunction<FIRST, SECOND, THIRD, W> andThen(CheckedFunction<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (first, second, third) -> after.apply(apply(first, second, third));
    }

    default <R$> CheckedTriFunction<FIRST, SECOND, THIRD, R$> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third) -> after.apply(apply(first, second, third));
    }

    static <FIRST, SECOND, THIRD, R> CheckedTriFunction<FIRST, SECOND, THIRD, R> checked(TriFunction<FIRST, SECOND, THIRD, R> function) {
        return function::apply;
    }

    static <FIRST, SECOND, THIRD, R> TriFunction<FIRST, SECOND, THIRD, R> unchecked(CheckedTriFunction<FIRST, SECOND, THIRD, R> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, SECOND, THIRD, R> TriFunction<FIRST, SECOND, THIRD, R> unchecked(CheckedTriFunction<FIRST, SECOND, THIRD, R> checked, Function<Exception, R> exceptionHandler) {
        return (first, second, third) -> {
            try {
                return checked.apply(first, second, third);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
