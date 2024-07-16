package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> {

    R apply(FIRST first, SECOND second, THIRD third, FOURTH forth);

    default <R$> TetraFunction<FIRST, SECOND, THIRD, FOURTH, R$> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third, forth) -> after.apply(apply(first, second, third, forth));
    }

    default <FIRST$, SECOND$, THIRD$, FOURTH$> TetraFunction<FIRST$, SECOND$, THIRD$, FOURTH$, R> compose(
            Function<? super FIRST$, ? extends FIRST> firstFunction,
            Function<? super SECOND$, ? extends SECOND> secondFunction,
            Function<? super THIRD$, ? extends THIRD> thirdFunction,
            Function<? super FOURTH$, ? extends FOURTH> forthFunction
    ) {
        return (first, second, third, forth) -> apply(firstFunction.apply(first), secondFunction.apply(second), thirdFunction.apply(third), forthFunction.apply(forth));
    }
}
