package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedTriConsumer<FIRST, SECOND, THIRD, E extends Throwable> {

    void accept(FIRST first, SECOND second, THIRD third) throws E;

    default TriConsumer<FIRST, SECOND, THIRD> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default CheckedTriConsumer<FIRST, SECOND, THIRD, E> andThen(TriConsumer<? super FIRST, ? super SECOND, ? super THIRD> after) {
        Objects.requireNonNull(after);
        return (first, second, third) -> { accept(first, second, third); after.accept(first, second, third); };
    }

    default CheckedTriConsumer<FIRST, SECOND, THIRD, E> andThen(CheckedTriConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? extends E> after) {
        Objects.requireNonNull(after);
        return (first, second, third) -> { accept(first, second, third); after.accept(first, second, third); };
    }

    static <FIRST, SECOND, THIRD, E extends Throwable> CheckedTriConsumer<FIRST, SECOND, THIRD, E> checked(TriConsumer<FIRST, SECOND, THIRD> unchecked) {
        return unchecked::accept;
    }

    static <FIRST, SECOND, THIRD, E extends Throwable> TriConsumer<FIRST, SECOND, THIRD> unchecked(CheckedTriConsumer<FIRST, SECOND, THIRD, E> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST, SECOND, THIRD, E extends Throwable> TriConsumer<FIRST, SECOND, THIRD> unchecked(CheckedTriConsumer<FIRST, SECOND, THIRD, E> checked, Consumer<E> exceptionHandler) {
        return (first, second, third) -> accept(first, second, third, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, E extends Throwable> void accept(FIRST first, SECOND second, THIRD third, CheckedTriConsumer<FIRST, SECOND, THIRD, E> checked, Consumer<E> exceptionHandler) {
        try {
            checked.accept(first, second, third);
        } catch (Throwable e) {
            exceptionHandler.accept((E)e);
        }
    }

    static <FIRST, SECOND, THIRD, E extends Throwable> void accept(FIRST first, SECOND second, THIRD third, CheckedTriConsumer<FIRST, SECOND, THIRD, E> checked) {
        accept(first, second, third, checked, ExceptionHandler.asConsumer());
    }
}