package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<FIRST, SECOND, THIRD, R> {

    R apply(FIRST first, SECOND second, THIRD third);

    default <R$> TriFunction<FIRST, SECOND, THIRD, R$> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third) -> after.apply(apply(first, second, third));
    }

    default <FIRST$, SECOND$, THIRD$> TriFunction<FIRST$, SECOND$, THIRD$, R> compose(
            Function<? super FIRST$, ? extends FIRST> firstFunction,
            Function<? super SECOND$, ? extends SECOND> secondFunction,
            Function<? super THIRD$, ? extends THIRD> thirdFunction
    ) {
        return (first, second, third) -> apply(firstFunction.apply(first), secondFunction.apply(second), thirdFunction.apply(third));
    }
}
