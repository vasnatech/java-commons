package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedTriPredicate<FIRST, SECOND, THIRD, E extends Throwable> {

    boolean test(FIRST first, SECOND second, THIRD third) throws E;

    default TriPredicate<FIRST, SECOND, THIRD> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default TriPredicate<FIRST, SECOND, THIRD> unchecked(Predicate<E> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD, E> and(CheckedTriPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third) -> test(first, second, third) && checked.test(first, second, third);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD, E> and(TriPredicate<? super FIRST, ? super SECOND, ? super THIRD> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third) -> test(first, second, third) && unchecked.test(first, second, third);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD, E> negate() {
        return (first, second, third) -> !test(first, second, third);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD, E> or(CheckedTriPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third) -> test(first, second, third) || checked.test(first, second, third);
    }

    default CheckedTriPredicate<FIRST, SECOND, THIRD, E> or(TriPredicate<? super FIRST, ? super SECOND, ? super THIRD> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third) -> test(first, second, third) || unchecked.test(first, second, third);
    }

    static <FIRST, SECOND, THIRD, E extends Throwable> CheckedTriPredicate<FIRST, SECOND, THIRD, E> checked(TriPredicate<FIRST, SECOND, THIRD> unchecked) {
        return unchecked::test;
    }

    static <FIRST, SECOND, THIRD, E extends Throwable> TriPredicate<FIRST, SECOND, THIRD> unchecked(CheckedTriPredicate<FIRST, SECOND, THIRD, E> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, SECOND, THIRD, E extends Throwable> TriPredicate<FIRST, SECOND, THIRD> unchecked(CheckedTriPredicate<FIRST, SECOND, THIRD, E> checked, Predicate<E> exceptionHandler) {
        return (first, second, third) -> test(first, second, third, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, E extends Throwable> boolean test(FIRST first, SECOND second, THIRD third, CheckedTriPredicate<FIRST, SECOND, THIRD, E> checked, Predicate<E> exceptionHandler) {
        try {
            return checked.test(first, second, third);
        } catch (Throwable e) {
            return exceptionHandler.test((E)e);
        }
    }

    static <FIRST, SECOND, THIRD, E extends Throwable> boolean test(FIRST first, SECOND second, THIRD third, CheckedTriPredicate<FIRST, SECOND, THIRD, E> checked) {
        return test(first, second, third, checked, ExceptionHandler.asPredicate());
    }
}
