package com.vasnatech.commons.function;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface CheckedSupplier<T> extends Callable<T> {

    T get() throws Exception;

    @Override
    default T call() throws Exception {
        return get();
    }

    default Supplier<T> unchecked() {
        return unchecked(this, ExceptionHandler.asFunction());
    }

    default Supplier<T> unchecked(Function<Exception, T> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <T> Supplier<T> unchecked(CheckedSupplier<T> checked) {
        return unchecked(checked, ExceptionHandler.asFunction());
    }

    static <T> Supplier<T> unchecked(CheckedSupplier<T> checked, Function<Exception, T> exceptionHandler) {
        return () -> {
            try {
                return checked.get();
            } catch (Exception e) {
                return exceptionHandler.apply(e);
            }
        };
    }

    static <T> CheckedSupplier<T> checked(Supplier<T> supplier) {
        return supplier::get;
    }
}
