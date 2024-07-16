package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedBiConsumer<FIRST, SECOND> {

    void accept(FIRST first, SECOND second) throws Exception;

    default BiConsumer<FIRST, SECOND> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default BiConsumer<FIRST, SECOND> unchecked(Consumer<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedBiConsumer<FIRST, SECOND> andThen(CheckedBiConsumer<? super FIRST, ? super SECOND> after) {
        Objects.requireNonNull(after);
        return (first, second) -> { accept(first, second); after.accept(first, second); };
    }

    default CheckedBiConsumer<FIRST, SECOND> andThen(BiConsumer<? super FIRST, ? super SECOND> after) {
        Objects.requireNonNull(after);
        return (first, second) -> { accept(first, second); after.accept(first, second); };
    }

    static <FIRST, SECOND> CheckedBiConsumer<FIRST, SECOND> checked(BiConsumer<FIRST, SECOND> unchecked) {
        return unchecked::accept;
    }

    static <FIRST, SECOND> BiConsumer<FIRST, SECOND> unchecked(CheckedBiConsumer<FIRST, SECOND> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST, SECOND> BiConsumer<FIRST, SECOND> unchecked(CheckedBiConsumer<FIRST, SECOND> checked, Consumer<Exception> exceptionHandler) {
        return (first, second) -> {
            try {
                checked.accept(first, second);
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
}
