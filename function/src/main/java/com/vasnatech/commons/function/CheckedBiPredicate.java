package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedBiPredicate<FIRST, SECOND> {

    boolean test(FIRST first, SECOND second) throws Exception;

    default BiPredicate<FIRST, SECOND> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default BiPredicate<FIRST, SECOND> unchecked(Predicate<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedBiPredicate<FIRST, SECOND> and(CheckedBiPredicate<? super FIRST, ? super SECOND> checked) {
        Objects.requireNonNull(checked);
        return (first, second) -> test(first, second) && checked.test(first, second);
    }

    default CheckedBiPredicate<FIRST, SECOND> and(BiPredicate<? super FIRST, ? super SECOND> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second) -> test(first, second) && unchecked.test(first, second);
    }

    default CheckedBiPredicate<FIRST, SECOND> negate() {
        return (first, second) -> !test(first, second);
    }

    default CheckedBiPredicate<FIRST, SECOND> or(CheckedBiPredicate<? super FIRST, ? super SECOND> checked) {
        Objects.requireNonNull(checked);
        return (first, second) -> test(first, second) || checked.test(first, second);
    }

    default CheckedBiPredicate<FIRST, SECOND> or(BiPredicate<? super FIRST, ? super SECOND> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second) -> test(first, second) || unchecked.test(first, second);
    }

    static <FIRST, SECOND> CheckedBiPredicate<FIRST, SECOND> checked(BiPredicate<FIRST, SECOND> unchecked) {
        return unchecked::test;
    }

    static <FIRST, SECOND> BiPredicate<FIRST, SECOND> unchecked(CheckedBiPredicate<FIRST, SECOND> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, SECOND> BiPredicate<FIRST, SECOND> unchecked(CheckedBiPredicate<FIRST, SECOND> checked, Predicate<Exception> exceptionHandler) {
        return (first, second) -> {
            try {
                return checked.test(first, second);
            } catch (Exception e) {
                return exceptionHandler.test(e);
            }
        };
    }
}
