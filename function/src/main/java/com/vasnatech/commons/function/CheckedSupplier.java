package com.vasnatech.commons.function;

import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface CheckedSupplier<FIRST, E extends Throwable> {

    FIRST get() throws E;

    default Supplier<FIRST> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default Supplier<FIRST> unchecked(Function<E, FIRST> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <FIRST, E extends Throwable> Supplier<FIRST> unchecked(CheckedSupplier<FIRST, E> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST, E extends Throwable> Supplier<FIRST> unchecked(CheckedSupplier<FIRST, E> checked, Function<E, FIRST> exceptionHandler) {
        return () -> get(checked, exceptionHandler);
    }

    static <FIRST, E extends Throwable> CheckedSupplier<FIRST, E> checked(Supplier<FIRST> supplier) {
        return supplier::get;
    }

    @SuppressWarnings("unchecked")
    static <FIRST, E extends Throwable> FIRST get(CheckedSupplier<FIRST, E> checked, Function<E, FIRST> exceptionHandler) {
        try {
            return checked.get();
        } catch (Throwable e) {
            return exceptionHandler.apply((E)e);
        }
    }

    static <FIRST, E extends Throwable> FIRST get(CheckedSupplier<FIRST, E> checked) {
        return get(checked, ExceptionHandler.asFunction());
    }
}
