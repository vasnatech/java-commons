package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedBiConsumer<FIRST, SECOND, E extends Throwable> {

    void accept(FIRST first, SECOND second) throws E;

    default BiConsumer<FIRST, SECOND> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default BiConsumer<FIRST, SECOND> unchecked(Consumer<E> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    default CheckedBiConsumer<FIRST, SECOND, E> andThen(CheckedBiConsumer<? super FIRST, ? super SECOND, ? extends E> after) {
        Objects.requireNonNull(after);
        return (first, second) -> { accept(first, second); after.accept(first, second); };
    }

    default CheckedBiConsumer<FIRST, SECOND, E> andThen(BiConsumer<? super FIRST, ? super SECOND> after) {
        Objects.requireNonNull(after);
        return (first, second) -> { accept(first, second); after.accept(first, second); };
    }

    static <FIRST, SECOND, E extends Throwable> CheckedBiConsumer<FIRST, SECOND, E> checked(BiConsumer<FIRST, SECOND> unchecked) {
        return unchecked::accept;
    }

    static <FIRST, SECOND, E extends Throwable> BiConsumer<FIRST, SECOND> unchecked(CheckedBiConsumer<FIRST, SECOND, E> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST, SECOND, E extends Throwable> BiConsumer<FIRST, SECOND> unchecked(CheckedBiConsumer<FIRST, SECOND, E> checked, Consumer<E> exceptionHandler) {
        return (first, second) -> accept(first, second, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, E extends Throwable> void accept(FIRST first, SECOND second, CheckedBiConsumer<FIRST, SECOND, E> checked, Consumer<E> exceptionHandler) {
        try {
            checked.accept(first, second);
        } catch (Throwable e) {
            exceptionHandler.accept((E)e);
        }
    }

    static <FIRST, SECOND, E extends Throwable> void accept(FIRST first, SECOND second, CheckedBiConsumer<FIRST, SECOND, E> checked) {
        accept(first, second, checked, ExceptionHandler.asConsumer());
    }
}
