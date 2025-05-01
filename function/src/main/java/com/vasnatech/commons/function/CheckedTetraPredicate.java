package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E extends Throwable> {

    boolean test(FIRST first, SECOND second, THIRD third, FOURTH fourth) throws E;

    default TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked(Predicate<E> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> and(CheckedTetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third, fourth) -> test(first, second, third, fourth) && checked.test(first, second, third, fourth);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> and(TetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third, fourth) -> test(first, second, third, fourth) && unchecked.test(first, second, third, fourth);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> negate() {
        return (first, second, third, fourth) -> !test(first, second, third, fourth);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> or(CheckedTetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third, fourth) -> test(first, second, third, fourth) || checked.test(first, second, third, fourth);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> or(TetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third, fourth) -> test(first, second, third, fourth) || unchecked.test(first, second, third, fourth);
    }

    static <FIRST, SECOND, THIRD, FOURTH, E extends Throwable> CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> checked(TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked) {
        return unchecked::test;
    }

    static <FIRST, SECOND, THIRD, FOURTH, E extends Throwable> TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, SECOND, THIRD, FOURTH, E extends Throwable> TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> checked, Predicate<E> exceptionHandler) {
        return (first, second, third, fourth) -> test(first, second, third, fourth, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH, E extends Throwable> boolean test(FIRST first, SECOND second, THIRD third, FOURTH fourth, CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> checked, Predicate<E> exceptionHandler) {
        try {
            return checked.test(first, second, third, fourth);
        } catch (Throwable e) {
            return exceptionHandler.test((E)e);
        }
    }

    static <FIRST, SECOND, THIRD, FOURTH, E extends Throwable> boolean test(FIRST first, SECOND second, THIRD third, FOURTH fourth, CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH, E> checked) {
        return test(first, second, third, fourth, checked, ExceptionHandler.asPredicate());
    }
}
