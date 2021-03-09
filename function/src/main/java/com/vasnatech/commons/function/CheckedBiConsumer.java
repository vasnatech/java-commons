package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedBiConsumer<T, U> {

    void accept(T t, U u) throws Exception;

    default BiConsumer<T, U> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default BiConsumer<T, U> unchecked(Consumer<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedBiConsumer<T, U> andThen(CheckedBiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);
        return (t, u) -> { accept(t, u); after.accept(t, u); };
    }

    default CheckedBiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);
        return (t, u) -> { accept(t, u); after.accept(t, u); };
    }

    static <T, U> CheckedBiConsumer<T, U> checked(BiConsumer<T, U> unchecked) {
        return unchecked::accept;
    }

    static <T, U> BiConsumer<T, U> unchecked(CheckedBiConsumer<T, U> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <T, U> BiConsumer<T, U> unchecked(CheckedBiConsumer<T, U> checked, Consumer<Exception> exceptionHandler) {
        return (t, u) -> {
            try {
                checked.accept(t, u);
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
}
