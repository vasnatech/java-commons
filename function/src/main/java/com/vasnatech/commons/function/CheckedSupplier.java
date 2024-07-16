package com.vasnatech.commons.function;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface CheckedSupplier<FIRST> extends Callable<FIRST> {

    FIRST get() throws Exception;

    @Override
    default FIRST call() throws Exception {
        return get();
    }

    default Supplier<FIRST> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default Supplier<FIRST> unchecked(Function<Exception, FIRST> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <FIRST> Supplier<FIRST> unchecked(CheckedSupplier<FIRST> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <FIRST> Supplier<FIRST> unchecked(CheckedSupplier<FIRST> checked, Function<Exception, FIRST> exceptionHandler) {
        return () -> {
            try {
                return checked.get();
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }

    static <FIRST> CheckedSupplier<FIRST> checked(Supplier<FIRST> supplier) {
        return supplier::get;
    }
}
