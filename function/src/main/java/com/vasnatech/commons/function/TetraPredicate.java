package com.vasnatech.commons.function;

@FunctionalInterface
public interface TetraPredicate<FIRST, SECOND, THIRD, FOURTH> {

    boolean test(FIRST first, SECOND second, THIRD third, FOURTH forth);

    default TetraPredicate<FIRST, SECOND, THIRD, FOURTH> and(TetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> other) {
        return (first, second, third, forth) -> test(first, second, third, forth) && other.test(first, second, third, forth);
    }

    default TetraPredicate<FIRST, SECOND, THIRD, FOURTH> negate() {
        return (first, second, third, forth) -> !test(first, second, third, forth);
    }

    default TetraPredicate<FIRST, SECOND, THIRD, FOURTH> or(TetraPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> other) {
        return (first, second, third, forth) -> test(first, second, third, forth) || other.test(first, second, third, forth);
    }
}