package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedConsumer<T> {

    void accept(T t) throws Exception;

    default Consumer<T> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default Consumer<T> unchecked(Consumer<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedConsumer<T> andThen(CheckedConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }

    default CheckedConsumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }

    static <T> CheckedConsumer<T> checked(Consumer<T> unchecked) {
        return unchecked::accept;
    }

    static <T> Consumer<T> unchecked(CheckedConsumer<T> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <T> Consumer<T> unchecked(CheckedConsumer<T> checked, Consumer<Exception> exceptionHandler) {
        return t -> {
            try {
                checked.accept(t);
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
}
