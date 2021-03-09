package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedPredicate<T> {

    boolean test(T t) throws Exception;

    default Predicate<T> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default Predicate<T> unchecked(Predicate<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedPredicate<T> and(CheckedPredicate<? super T> checked) {
        Objects.requireNonNull(checked);
        return t -> test(t) && checked.test(t);
    }

    default CheckedPredicate<T> and(Predicate<? super T> unchecked) {
        Objects.requireNonNull(unchecked);
        return t -> test(t) && unchecked.test(t);
    }

    default CheckedPredicate<T> negate() {
        return t -> !test(t);
    }

    default CheckedPredicate<T> or(CheckedPredicate<? super T> checked) {
        Objects.requireNonNull(checked);
        return t -> test(t) || checked.test(t);
    }

    default CheckedPredicate<T> or(Predicate<? super T> unchecked) {
        Objects.requireNonNull(unchecked);
        return t -> test(t) || unchecked.test(t);
    }


    static <T> CheckedPredicate<T> checked(Predicate<T> unchecked) {
        return unchecked::test;
    }
    static <T> Predicate<T> unchecked(CheckedPredicate<T> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <T> Predicate<T> unchecked(CheckedPredicate<T> checked, Predicate<Exception> exceptionHandler) {
        return t -> {
            try {
                return checked.test(t);
            } catch (Exception e) {
                return exceptionHandler.test(e);
            }
        };
    }

    static <T> CheckedPredicate<T> isEqual(Object targetRef) {
        return targetRef == null ? Objects::isNull : targetRef::equals;
    }

    static <T> CheckedPredicate<T> negate(CheckedPredicate<? super T> checked) {
        Objects.requireNonNull(checked);
        return t -> !checked.test(t);
    }
}
