package com.vasnatech.commons.function;

@FunctionalInterface
public interface TetraConsumer<FIRST, SECOND, THIRD, FOURTH> {

    void accept(FIRST first, SECOND second, THIRD third, FOURTH forth);

    default TetraConsumer<FIRST, SECOND, THIRD, FOURTH> andThen(TetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> after) {
        return (first, second, third, forth) -> {
            accept(first, second, third, forth);
            after.accept(first, second, third, forth);
        };
    }
}