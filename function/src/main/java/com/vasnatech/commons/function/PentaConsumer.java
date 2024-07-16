package com.vasnatech.commons.function;

@FunctionalInterface
public interface PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> {

    void accept(FIRST first, SECOND second, THIRD third, FOURTH forth, FIFTH fifth);

    default PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> andThen(PentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> after) {
        return (first, second, third, forth, fifth) -> {
            accept(first, second, third, forth, fifth);
            after.accept(first, second, third, forth, fifth);
        };
    }
}