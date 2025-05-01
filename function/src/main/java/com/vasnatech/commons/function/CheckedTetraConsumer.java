package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH, E extends Throwable> {

    void accept(FIRST first, SECOND second, THIRD third, FOURTH fourth) throws E;

    default TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH, E> andThen(TetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth) -> { accept(first, second, third, fourth); after.accept(first, second, third, fourth); };
    }

    default CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH, E> andThen(CheckedTetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? extends E> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth) -> { accept(first, second, third, fourth); after.accept(first, second, third, fourth); };
    }

    static <FIRST, SECOND, THIRD, FOURTH, E extends Throwable> CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH, E> checked(TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked) {
        return unchecked::accept;
    }

    static <FIRST, SECOND, THIRD, FOURTH, E extends Throwable> TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH, E> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST, SECOND, THIRD, FOURTH, E extends Throwable> TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH, E> checked, Consumer<E> exceptionHandler) {
        return (first, second, third, fourth) -> accept(first, second, third, fourth, checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH, E extends Throwable> void accept(FIRST first, SECOND second, THIRD third, FOURTH fourth, CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH, E> checked, Consumer<E> exceptionHandler) {
        try {
            checked.accept(first, second, third, fourth);
        } catch (Throwable e) {
            exceptionHandler.accept((E)e);
        }
    }
}