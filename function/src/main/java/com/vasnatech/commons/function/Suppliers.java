package com.vasnatech.commons.function;

import java.util.function.Supplier;

public class Suppliers {

    public static <T> Supplier<T> constant(T result) {
        return () -> result;
    }

    public static <T> Supplier<T> of(Supplier<T> supplier) {
        return supplier;
    }

    public static <T, E extends Throwable> Supplier<T> unchecked(CheckedSupplier<T, E> checked) {
        return checked.unchecked();
    }

    public static <T, E extends Throwable> CheckedSupplier<T, E> checked(Supplier<T> unchecked) {
        return CheckedSupplier.checked(unchecked);
    }
}
