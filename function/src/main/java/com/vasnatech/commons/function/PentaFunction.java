package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> {

    R apply(FIRST first, SECOND second, THIRD third, FOURTH forth, FIFTH fifth);

    default <R$> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R$> andThen(Function<? super R, ? extends R$> after) {
        Objects.requireNonNull(after);
        return (first, second, third, forth, fifth) -> after.apply(apply(first, second, third, forth, fifth));
    }

    default <FIRST$, SECOND$, THIRD$, FOURTH$, FIFTH$> PentaFunction<FIRST$, SECOND$, THIRD$, FOURTH$, FIFTH$, R> compose(
            Function<? super FIRST$, ? extends FIRST> firstFunction,
            Function<? super SECOND$, ? extends SECOND> secondFunction,
            Function<? super THIRD$, ? extends THIRD> thirdFunction,
            Function<? super FOURTH$, ? extends FOURTH> forthFunction,
            Function<? super FIFTH$, ? extends FIFTH> fifthFunction
    ) {
        return (first, second, third, forth, fifth) -> apply(firstFunction.apply(first), secondFunction.apply(second), thirdFunction.apply(third), forthFunction.apply(forth), fifthFunction.apply(fifth));
    }
}
