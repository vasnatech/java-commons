package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedBiPredicate<T, U> {

    boolean test(T t, U u) throws Exception;

    default BiPredicate<T, U> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default BiPredicate<T, U> unchecked(Predicate<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedBiPredicate<T, U> and(CheckedBiPredicate<? super T, ? super U> checked) {
        Objects.requireNonNull(checked);
        return (t, u) -> test(t, u) && checked.test(t, u);
    }

    default CheckedBiPredicate<T, U> and(BiPredicate<? super T, ? super U> unchecked) {
        Objects.requireNonNull(unchecked);
        return (t, u) -> test(t, u) && unchecked.test(t, u);
    }

    default CheckedBiPredicate<T, U> negate() {
        return (t, u) -> !test(t, u);
    }

    default CheckedBiPredicate<T, U> or(CheckedBiPredicate<? super T, ? super U> checked) {
        Objects.requireNonNull(checked);
        return (t, u) -> test(t, u) || checked.test(t, u);
    }

    default CheckedBiPredicate<T, U> or(BiPredicate<? super T, ? super U> unchecked) {
        Objects.requireNonNull(unchecked);
        return (t, u) -> test(t, u) || unchecked.test(t, u);
    }

    static <T, U> CheckedBiPredicate<T, U> checked(BiPredicate<T, U> unchecked) {
        return unchecked::test;
    }

    static <T, U> BiPredicate<T, U> unchecked(CheckedBiPredicate<T, U> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <T, U> BiPredicate<T, U> unchecked(CheckedBiPredicate<T, U> checked, Predicate<Exception> exceptionHandler) {
        return (t, u) -> {
            try {
                return checked.test(t, u);
            } catch (Exception e) {
                return exceptionHandler.test(e);
            }
        };
    }
}
