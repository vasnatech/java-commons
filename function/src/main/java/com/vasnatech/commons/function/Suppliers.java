package com.vasnatech.commons.function;

import java.util.function.Supplier;

public class Suppliers {

    public static <T> Supplier<T> constant(T result) {
        return () -> result;
    }

    public static <T> Supplier<T> of(Supplier<T> supplier) {
        return supplier;
    }

    public static <T> Supplier<T> unchecked(CheckedSupplier<T> checked) {
        return checked.unchecked();
    }

    public static <T> CheckedSupplier<T> checked(Supplier<T> unchecked) {
        return CheckedSupplier.checked(unchecked);
    }
}
