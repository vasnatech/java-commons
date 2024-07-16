package com.vasnatech.commons.function;

@FunctionalInterface
public interface PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> {

    boolean test(FIRST first, SECOND second, THIRD third, FOURTH forth, FIFTH fifth);

    default PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> and(PentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> other) {
        return (first, second, third, forth, fifth) -> test(first, second, third, forth, fifth) && other.test(first, second, third, forth, fifth);
    }

    default PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> negate() {
        return (first, second, third, forth, fifth) -> !test(first, second, third, forth, fifth);
    }

    default PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> or(PentaPredicate<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> other) {
        return (first, second, third, forth, fifth) -> test(first, second, third, forth, fifth) || other.test(first, second, third, forth, fifth);
    }
}