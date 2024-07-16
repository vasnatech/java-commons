package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> {

    boolean test(FIRST first, SECOND second, THIRD third, FOURTH fourth) throws Exception;

    default TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked() {
        return unchecked(this, ExceptionHandler.asPredicate());
    }

    default TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked(Predicate<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> and(CheckedTetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third, fourth) -> test(first, second, third, fourth) && checked.test(first, second, third, fourth);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> and(TetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third, fourth) -> test(first, second, third, fourth) && unchecked.test(first, second, third, fourth);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> negate() {
        return (first, second, third, fourth) -> !test(first, second, third, fourth);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> or(CheckedTetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> checked) {
        Objects.requireNonNull(checked);
        return (first, second, third, fourth) -> test(first, second, third, fourth) || checked.test(first, second, third, fourth);
    }

    default CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> or(TetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> unchecked) {
        Objects.requireNonNull(unchecked);
        return (first, second, third, fourth) -> test(first, second, third, fourth) || unchecked.test(first, second, third, fourth);
    }

    static <FIRST, SECOND, THIRD, FOURTH> CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> checked(TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked) {
        return unchecked::test;
    }

    static <FIRST, SECOND, THIRD, FOURTH> TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> checked) {
        return unchecked(checked, ExceptionHandler.asPredicate());
    }

    static <FIRST, SECOND, THIRD, FOURTH> TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> checked, Predicate<Exception> exceptionHandler) {
        return (first, second, third, fourth) -> {
            try {
                return checked.test(first, second, third, fourth);
            } catch (Exception e) {
                return exceptionHandler.test(e);
            }
        };
    }
}
