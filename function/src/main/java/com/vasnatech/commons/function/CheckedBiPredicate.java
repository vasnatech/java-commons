package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedBiPredicate<FIRST, SECOND, E extends Throwable> {

    boolean test(FIRST first, SECOND second) throws E;

    default BiPredicate<FIRST, SECOND> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default BiPredicate<FIRST, SECOND> unchecked(Predicate<E> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedBiPredicate<FIRST, SECOND, E> and(CheckedBiPredicate<? super FIRST, ? super SECOND, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return (first, second) -> test(first, second) && checked.test(first, second);
    }

    default CheckedBiPredicate<FIRST, SECOND, E> and(BiPredicate<? super FIRST, ? super SECOND> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second) -> test(first, second) && unchecked.test(first, second);
    }

    default CheckedBiPredicate<FIRST, SECOND, E> negate() {
        return (first, second) -> !test(first, second);
    }

    default CheckedBiPredicate<FIRST, SECOND, E> or(CheckedBiPredicate<? super FIRST, ? super SECOND, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return (first, second) -> test(first, second) || checked.test(first, second);
    }

    default CheckedBiPredicate<FIRST, SECOND, E> or(BiPredicate<? super FIRST, ? super SECOND> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second) -> test(first, second) || unchecked.test(first, second);
    }

    static <FIRST, SECOND, E extends Throwable> CheckedBiPredicate<FIRST, SECOND, E> checked(BiPredicate<FIRST, SECOND> unchecked) {
        return unchecked::test;
    }

    static <FIRST, SECOND, E extends Throwable> BiPredicate<FIRST, SECOND> unchecked(CheckedBiPredicate<FIRST, SECOND, E> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, SECOND, E extends Throwable> BiPredicate<FIRST, SECOND> unchecked(CheckedBiPredicate<FIRST, SECOND, E> checked, Predicate<E> exceptionHandler) {
        return (first, second) -> test(first, second, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, E extends Throwable> boolean test(FIRST first, SECOND second, CheckedBiPredicate<FIRST, SECOND, E> checked, Predicate<E> exceptionHandler) {
        try {
            return checked.test(first, second);
        } catch (Throwable e) {
            return exceptionHandler.test((E)e);
        }
    }

    static <FIRST, SECOND, E extends Throwable> boolean test(FIRST first, SECOND second, CheckedBiPredicate<FIRST, SECOND, E> checked) {
        return test(first, second, checked, ExceptionHandler.asPredicate());
    }
}
