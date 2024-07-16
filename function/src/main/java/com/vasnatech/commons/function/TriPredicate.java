package com.vasnatech.commons.function;

@FunctionalInterface
public interface TriPredicate<FIRST, SECOND, THIRD> {

    boolean test(FIRST first, SECOND second, THIRD third);

    default TriPredicate<FIRST, SECOND, THIRD> and(TriPredicate<? super FIRST, ? super SECOND, ? super THIRD> other) {
        return (first, second, third) -> test(first, second, third) && other.test(first, second, third);
    }

    default TriPredicate<FIRST, SECOND, THIRD> negate() {
        return (first, second, third) -> !test(first, second, third);
    }

    default TriPredicate<FIRST, SECOND, THIRD> or(TriPredicate<? super FIRST, ? super SECOND, ? super THIRD> other) {
        return (first, second, third) -> test(first, second, third) || other.test(first, second, third);
    }
}