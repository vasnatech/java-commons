package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedTriPredicate<T, U, V> {

    boolean test(T t, U u, V v) throws Exception;

    default TriPredicate<T, U, V> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default TriPredicate<T, U, V> unchecked(Predicate<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedTriPredicate<T, U, V> and(CheckedTriPredicate<? super T, ? super U, ? super V> checked) {
        Objects.requireNonNull(checked);
        return (t, u, v) -> test(t, u, v) && checked.test(t, u, v);
    }

    default CheckedTriPredicate<T, U, V> and(TriPredicate<? super T, ? super U, ? super V> unchecked) {
        Objects.requireNonNull(unchecked);
        return (t, u, v) -> test(t, u, v) && unchecked.test(t, u, v);
    }

    default CheckedTriPredicate<T, U, V> negate() {
        return (t, u, v) -> !test(t, u, v);
    }

    default CheckedTriPredicate<T, U, V> or(CheckedTriPredicate<? super T, ? super U, ? super V> checked) {
        Objects.requireNonNull(checked);
        return (t, u, v) -> test(t, u, v) || checked.test(t, u, v);
    }

    default CheckedTriPredicate<T, U, V> or(TriPredicate<? super T, ? super U, ? super V> unchecked) {
        Objects.requireNonNull(unchecked);
        return (t, u, v) -> test(t, u, v) || unchecked.test(t, u, v);
    }

    static <T, U, V> CheckedTriPredicate<T, U, V> checked(TriPredicate<T, U, V> unchecked) {
        return unchecked::test;
    }

    static <T, U, V> TriPredicate<T, U, V> unchecked(CheckedTriPredicate<T, U, V> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <T, U, V> TriPredicate<T, U, V> unchecked(CheckedTriPredicate<T, U, V> checked, Predicate<Exception> exceptionHandler) {
        return (t, u, v) -> {
            try {
                return checked.test(t, u, v);
            } catch (Exception e) {
                return exceptionHandler.test(e);
            }
        };
    }
}
