package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> {

    void accept(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) throws E;

    default PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> andThen(PentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth, fifth) -> { accept(first, second, third, fourth, fifth); after.accept(first, second, third, fourth, fifth); };
    }

    default CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> andThen(CheckedPentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH, ? extends E> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth, fifth) -> { accept(first, second, third, fourth, fifth); after.accept(first, second, third, fourth, fifth); };
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked(PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked) {
        return unchecked::accept;
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked, Consumer<E> exceptionHandler) {
        return (first, second, third, fourth, fifth) -> accept(first, second, third, fourth, fifth, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> void accept(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth, CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked, Consumer<E> exceptionHandler) {
        try {
            checked.accept(first, second, third, fourth, fifth);
        } catch (Throwable e) {
            exceptionHandler.accept((E)e);
        }
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, E extends Throwable> void accept(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth, CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH, E> checked) {
        accept(first, second, third, fourth, fifth, checked, ExceptionHandler.asConsumer());
    }
}