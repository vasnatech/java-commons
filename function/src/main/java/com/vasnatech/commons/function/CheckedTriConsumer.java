package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedTriConsumer<FIRST, SECOND, THIRD> {

    void accept(FIRST first, SECOND second, THIRD third) throws Exception;

    default TriConsumer<FIRST, SECOND, THIRD> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default CheckedTriConsumer<FIRST, SECOND, THIRD> andThen(TriConsumer<? super FIRST, ? super SECOND, ? super THIRD> after) {
        Objects.requireNonNull(after);
        return (first, second, third) -> { accept(first, second, third); after.accept(first, second, third); };
    }

    default CheckedTriConsumer<FIRST, SECOND, THIRD> andThen(CheckedTriConsumer<? super FIRST, ? super SECOND, ? super THIRD> after) {
        Objects.requireNonNull(after);
        return (first, second, third) -> { accept(first, second, third); after.accept(first, second, third); };
    }

    static <FIRST, SECOND, THIRD> CheckedTriConsumer<FIRST, SECOND, THIRD> checked(TriConsumer<FIRST, SECOND, THIRD> unchecked) {
        return unchecked::accept;
    }

    static <FIRST, SECOND, THIRD> TriConsumer<FIRST, SECOND, THIRD> unchecked(CheckedTriConsumer<FIRST, SECOND, THIRD> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST, SECOND, THIRD> TriConsumer<FIRST, SECOND, THIRD> unchecked(CheckedTriConsumer<FIRST, SECOND, THIRD> checked, Consumer<Exception> exceptionHandler) {
        return (first, second, third) -> {
            try {
                checked.accept(first, second, third);
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
}