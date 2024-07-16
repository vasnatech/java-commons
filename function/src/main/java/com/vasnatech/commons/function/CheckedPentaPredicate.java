package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> {

    boolean test(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) throws Exception;

    default PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(Predicate<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> and(CheckedPentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third, fourth, fifth) -> test(first, second, third, fourth, fifth) && checked.test(first, second, third, fourth, fifth);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> and(PentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third, fourth, fifth) -> test(first, second, third, fourth, fifth) && unchecked.test(first, second, third, fourth, fifth);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> negate() {
        return (first, second, third, fourth, fifth) -> !test(first, second, third, fourth, fifth);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> or(CheckedPentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third, fourth, fifth) -> test(first, second, third, fourth, fifth) || checked.test(first, second, third, fourth, fifth);
    }

    default CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> or(PentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third, fourth, fifth) -> test(first, second, third, fourth, fifth) || unchecked.test(first, second, third, fourth, fifth);
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked(PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked) {
        return unchecked::test;
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked, Predicate<Exception> exceptionHandler) {
        return (first, second, third, fourth, fifth) -> {
            try {
                return checked.test(first, second, third, fourth, fifth);
            } catch (Exception e) {
                return exceptionHandler.test(e);
            }
        };
    }
}
