package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH> {

    void accept(FIRST first, SECOND second, THIRD third, FOURTH fourth) throws Exception;

    default TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH> andThen(TetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth) -> { accept(first, second, third, fourth); after.accept(first, second, third, fourth); };
    }

    default CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH> andThen(CheckedTetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> after) {
        Objects.requireNonNull(after);
        return (first, second, third, fourth) -> { accept(first, second, third, fourth); after.accept(first, second, third, fourth); };
    }

    static <FIRST, SECOND, THIRD, FOURTH> CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH> checked(TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked) {
        return unchecked::accept;
    }

    static <FIRST, SECOND, THIRD, FOURTH> TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <FIRST, SECOND, THIRD, FOURTH> TetraConsumer<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraConsumer<FIRST, SECOND, THIRD, FOURTH> checked, Consumer<Exception> exceptionHandler) {
        return (first, second, third, fourth) -> {
            try {
                checked.accept(first, second, third, fourth);
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
}