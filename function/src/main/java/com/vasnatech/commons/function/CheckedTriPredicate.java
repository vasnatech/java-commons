package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedTriPredicate<FIRST, SECOND, THIRD> {

    boolean test(FIRST first, SECOND second, THIRD third) throws Exception;

    default TriPredicate<FIRST, SECOND, THIRD> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default TriPredicate<FIRST, SECOND, THIRD> unchecked(Predicate<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD> and(CheckedTriPredicate<? super FIRST, ? super SECOND, ? super THIRD> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third) -> test(first, second, third) && checked.test(first, second, third);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD> and(TriPredicate<? super FIRST, ? super SECOND, ? super THIRD> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third) -> test(first, second, third) && unchecked.test(first, second, third);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD> negate() {
        return (first, second, third) -> !test(first, second, third);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD> or(CheckedTriPredicate<? super FIRST, ? super SECOND, ? super THIRD> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third) -> test(first, second, third) || checked.test(first, second, third);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD> or(TriPredicate<? super FIRST, ? super SECOND, ? super THIRD> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third) -> test(first, second, third) || unchecked.test(first, second, third);
    }

    static <FIRST, SECOND, THIRD> CheckedTriPredicate<FIRST, SECOND, THIRD> checked(TriPredicate<FIRST, SECOND, THIRD> unchecked) {
        return unchecked::test;
    }

    static <FIRST, SECOND, THIRD> TriPredicate<FIRST, SECOND, THIRD> unchecked(CheckedTriPredicate<FIRST, SECOND, THIRD> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, SECOND, THIRD> TriPredicate<FIRST, SECOND, THIRD> unchecked(CheckedTriPredicate<FIRST, SECOND, THIRD> checked, Predicate<Exception> exceptionHandler) {
        return (first, second, third) -> {
            try {
                return checked.test(first, second, third);
            } catch (Exception e) {
                return exceptionHandler.test(e);
            }
        };
    }
}
