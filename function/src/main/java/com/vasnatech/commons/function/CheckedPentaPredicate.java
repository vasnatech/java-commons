package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> {

    boolean test(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) throws E;

    default PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(Predicate<E> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> and(CheckedPentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third, fourth, fifth) -> test(first, second, third, fourth, fifth) && checked.test(first, second, third, fourth, fifth);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> and(PentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third, fourth, fifth) -> test(first, second, third, fourth, fifth) && unchecked.test(first, second, third, fourth, fifth);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> negate() {
        return (first, second, third, fourth, fifth) -> !test(first, second, third, fourth, fifth);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> or(CheckedPentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third, fourth, fifth) -> test(first, second, third, fourth, fifth) || checked.test(first, second, third, fourth, fifth);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> or(PentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third, fourth, fifth) -> test(first, second, third, fourth, fifth) || unchecked.test(first, second, third, fourth, fifth);
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked(PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked) {
        return unchecked::test;
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked, Predicate<E> exceptionHandler) {
        return (first, second, third, fourth, fifth) -> test(first, second, third, fourth, fifth, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> boolean test(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth, CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked, Predicate<E> exceptionHandler) {
        try {
            return checked.test(first, second, third, fourth, fifth);
        } catch (Throwable e) {
            return exceptionHandler.test((E)e);
        }
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> boolean test(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth, CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked) {
        return test(first, second, third, fourth, fifth, checked, ExceptionHandler.asPredicate());
    }
}
