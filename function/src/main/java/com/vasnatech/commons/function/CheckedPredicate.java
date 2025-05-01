package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedPredicate<FIRST, E extends Throwable> {

    boolean test(FIRST first) throws E;

    default Predicate<FIRST> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default Predicate<FIRST> unchecked(Predicate<E> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedPredicate<FIRST, E> and(CheckedPredicate<? super FIRST, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return first -> test(first) && checked.test(first);
    }

    default CheckedPredicate<FIRST, E> and(Predicate<? super FIRST> unchecked) {
        Objects.requireNonNull(unchecked);
        return first -> test(first) && unchecked.test(first);
    }

    default CheckedPredicate<FIRST, E> negate() {
        return first -> !test(first);
    }

    default CheckedPredicate<FIRST, E> or(CheckedPredicate<? super FIRST, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return first -> test(first) || checked.test(first);
    }

    default CheckedPredicate<FIRST, E> or(Predicate<? super FIRST> unchecked) {
        Objects.requireNonNull(unchecked);
        return first -> test(first) || unchecked.test(first);
    }


    static <FIRST, E extends Throwable> CheckedPredicate<FIRST, E> checked(Predicate<FIRST> unchecked) {
        return unchecked::test;
    }

    static <FIRST, E extends Throwable> Predicate<FIRST> unchecked(CheckedPredicate<FIRST, E> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, E extends Throwable> Predicate<FIRST> unchecked(CheckedPredicate<FIRST, E> checked, Predicate<E> exceptionHandler) {
        return first -> test(first, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, E extends Throwable> boolean test(FIRST first, CheckedPredicate<FIRST, E> checked, Predicate<E> exceptionHandler) {
        try {
            return checked.test(first);
        } catch (Throwable e) {
            return exceptionHandler.test((E)e);
        }
    }

    static <FIRST, E extends Throwable> boolean test(FIRST first, CheckedPredicate<FIRST, E> checked) {
        return test(first, checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, E extends Throwable> CheckedPredicate<FIRST, E> isEqual(Object targetRef) {
        return targetRef == null ? Objects::isNull : targetRef::equals;
    }

    static <FIRST, E extends Throwable> CheckedPredicate<FIRST, E> negate(CheckedPredicate<? super FIRST, ? extends E> checked) {
        Objects.requireNonNull(checked);
        return t -> !checked.test(t);
    }
}
