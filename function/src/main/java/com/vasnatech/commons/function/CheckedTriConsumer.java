package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedTriConsumer<T, U, V> {

    void accept(T t, U u, V v) throws Exception;

    default TriConsumer<T, U, V> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default CheckedTriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);
        return (t, u, v) -> { accept(t, u, v); after.accept(t, u, v); };
    }

    default CheckedTriConsumer<T, U, V> andThen(CheckedTriConsumer<? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);
        return (t, u, v) -> { accept(t, u, v); after.accept(t, u, v); };
    }

    static <T, U, V> CheckedTriConsumer<T, U, V> checked(TriConsumer<T, U, V> unchecked) {
        return unchecked::accept;
    }

    static <T, U, V> TriConsumer<T, U, V> unchecked(CheckedTriConsumer<T, U, V> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <T, U, V> TriConsumer<T, U, V> unchecked(CheckedTriConsumer<T, U, V> checked, Consumer<Exception> exceptionHandler) {
        return (t, u, v) -> {
            try {
                checked.accept(t, u, v);
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
}