package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedPredicate<FIRST> {

    boolean test(FIRST first) throws Exception;

    default Predicate<FIRST> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default Predicate<FIRST> unchecked(Predicate<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedPredicate<FIRST> and(CheckedPredicate<? super FIRST> checked) {
        Objects.requireNonNull(checked);
        return first -> test(first) && checked.test(first);
    }

    default CheckedPredicate<FIRST> and(Predicate<? super FIRST> unchecked) {
        Objects.requireNonNull(unchecked);
        return first -> test(first) && unchecked.test(first);
    }

    default CheckedPredicate<FIRST> negate() {
        return first -> !test(first);
    }

    default CheckedPredicate<FIRST> or(CheckedPredicate<? super FIRST> checked) {
        Objects.requireNonNull(checked);
        return first -> test(first) || checked.test(first);
    }

    default CheckedPredicate<FIRST> or(Predicate<? super FIRST> unchecked) {
        Objects.requireNonNull(unchecked);
        return first -> test(first) || unchecked.test(first);
    }


    static <FIRST> CheckedPredicate<FIRST> checked(Predicate<FIRST> unchecked) {
        return unchecked::test;
    }
    static <FIRST> Predicate<FIRST> unchecked(CheckedPredicate<FIRST> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST> Predicate<FIRST> unchecked(CheckedPredicate<FIRST> checked, Predicate<Exception> exceptionHandler) {
        return first -> {
            try {
                return checked.test(first);
            } catch (Exception e) {
                return exceptionHandler.test(e);
            }
        };
    }

    static <FIRST> CheckedPredicate<FIRST> isEqual(Object targetRef) {
        return targetRef == null ? Objects::isNull : targetRef::equals;
    }

    static <FIRST> CheckedPredicate<FIRST> negate(CheckedPredicate<? super FIRST> checked) {
        Objects.requireNonNull(checked);
        return t -> !checked.test(t);
    }
}
