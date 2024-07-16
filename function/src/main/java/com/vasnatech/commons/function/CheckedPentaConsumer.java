package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> {

    void accept(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) throws Exception;

    default PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> andThen(PentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth, fifth) -> { accept(first, second, third, fourth, fifth); after.accept(first, second, third, fourth, fifth); };
    }

    default CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> andThen(CheckedPentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth, fifth) -> { accept(first, second, third, fourth, fifth); after.accept(first, second, third, fourth, fifth); };
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked(PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked) {
        return unchecked::accept;
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked, Consumer<Exception> exceptionHandler) {
        return (first, second, third, fourth, fifth) -> {
            try {
                checked.accept(first, second, third, fourth, fifth);
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
}