package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedBiFunction<FIRST, SECOND, R> {

    R apply(FIRST first, SECOND second) throws Exception;

    default BiFunction<FIRST, SECOND, R> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default BiFunction<FIRST, SECOND, R> unchecked(Function<Exception, R> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default <R$> CheckedBiFunction<FIRST, SECOND, R$> andThen(CheckedFunction<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second) -> after.apply(apply(first, second));
    }

    default <R$> CheckedBiFunction<FIRST, SECOND, R$> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second) -> after.apply(apply(first, second));
    }

    static <FIRST, SECOND, R> CheckedBiFunction<FIRST, SECOND, R> checked(BiFunction<FIRST, SECOND, R> function) {
        return function::apply;
    }

    static <FIRST, SECOND, R> BiFunction<FIRST, SECOND, R> unchecked(CheckedBiFunction<FIRST, SECOND, R> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, SECOND, R> BiFunction<FIRST, SECOND, R> unchecked(CheckedBiFunction<FIRST, SECOND, R> checked, Function<Exception, R> exceptionHandler) {
        return (first, second) -> {
            try {
                return checked.apply(first, second);
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }
}
