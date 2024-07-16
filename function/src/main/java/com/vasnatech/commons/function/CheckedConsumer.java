package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedConsumer<FIRST> {

    void accept(FIRST first) throws Exception;

    default Consumer<FIRST> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default Consumer<FIRST> unchecked(Consumer<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedConsumer<FIRST> andThen(CheckedConsumer<? super FIRST> after) {
        Objects.requireNonNull(after);
        return (FIRST first) -> { accept(first); after.accept(first); };
    }

    default CheckedConsumer<FIRST> andThen(Consumer<? super FIRST> after) {
        Objects.requireNonNull(after);
        return (FIRST first) -> { accept(first); after.accept(first); };
    }

    static <FIRST> CheckedConsumer<FIRST> checked(Consumer<FIRST> unchecked) {
        return unchecked::accept;
    }

    static <FIRST> Consumer<FIRST> unchecked(CheckedConsumer<FIRST> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST> Consumer<FIRST> unchecked(CheckedConsumer<FIRST> checked, Consumer<Exception> exceptionHandler) {
        return first -> {
            try {
                checked.accept(first);
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
}
