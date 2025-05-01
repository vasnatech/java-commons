package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedConsumer<FIRST, E extends Throwable> {

    void accept(FIRST first) throws E;

    default Consumer<FIRST> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default Consumer<FIRST> unchecked(Consumer<E> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedConsumer<FIRST, E> andThen(CheckedConsumer<? super FIRST, ? extends E> after) {
        Objects.requireNonNull(after);
        return (FIRST first) -> { accept(first); after.accept(first); };
    }

    default CheckedConsumer<FIRST, E> andThen(Consumer<? super FIRST> after) {
        Objects.requireNonNull(after);
        return (FIRST first) -> { accept(first); after.accept(first); };
    }

    static <FIRST, E extends Throwable> CheckedConsumer<FIRST, E> checked(Consumer<FIRST> unchecked) {
        return unchecked::accept;
    }

    static <FIRST, E extends Throwable> Consumer<FIRST> unchecked(CheckedConsumer<FIRST, E> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST, E extends Throwable> Consumer<FIRST> unchecked(CheckedConsumer<FIRST, E> checked, Consumer<E> exceptionHandler) {
        return first -> accept(first, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, E extends Throwable> void accept(FIRST first, CheckedConsumer<FIRST, E> checked, Consumer<E> exceptionHandler) {
        try {
            checked.accept(first);
        } catch (Throwable e) {
            exceptionHandler.accept((E)e);
        }
    }

    static <FIRST, E extends Throwable> void accept(FIRST first, CheckedConsumer<FIRST, E> checked) {
        accept(first, checked, ExceptionHandler.asConsumer());
    }
}
