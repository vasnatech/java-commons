package com.vasnatech.commons.function;

@FunctionalInterface
public interface TriConsumer<FIRST, SECOND, THIRD> {

    void accept(FIRST first, SECOND second, THIRD third);

    default TriConsumer<FIRST, SECOND, THIRD> andThen(TriConsumer<? super FIRST, ? super SECOND, ? super THIRD> after) {
        return (first, second, third) -> {
            accept(first, second, third);
            after.accept(first, second, third);
        };
    }
}