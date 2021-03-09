package com.vasnatech.commons.function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Consumers {

    private Consumers() {}



    private static final Consumer<?> DO_NOTHING = t -> {};

    @SuppressWarnings("unchecked")
    public static <T> Consumer<T> doNothing() {
        return (Consumer<T>) DO_NOTHING;
    }

    private static final BiConsumer<?, ?> DO_NOTHING_BI = (t, u) -> {};

    @SuppressWarnings("unchecked")
    public static <T, U> BiConsumer<T, U> doNothingBi() {
        return (BiConsumer<T, U>) DO_NOTHING_BI;
    }

    private static final TriConsumer<?, ?, ?> DO_NOTHING_TRI = (t, u, v) -> {};

    @SuppressWarnings("unchecked")
    public static <T, U, V> TriConsumer<T, U, V> doNothingTri() {
        return (TriConsumer<T, U, V>) DO_NOTHING_TRI;
    }

    public static <T> Consumer<T> of(Consumer<T> consumer) {
        return consumer;
    }

    public static <T> Consumer<T> unchecked(CheckedConsumer<T> checkedConsumer) {
        return checkedConsumer.unchecked();
    }

    public static <T> CheckedConsumer<T> checked(Consumer<T> unchecked) {
        return CheckedConsumer.checked(unchecked);
    }

    public static <T, U> BiConsumer<T, U> unchecked(CheckedBiConsumer<T, U> checked) {
        return checked.unchecked();
    }

    public static <T, U> CheckedBiConsumer<T, U> checked(BiConsumer<T, U> unchecked) {
        return CheckedBiConsumer.checked(unchecked);
    }

    public static <T, U, V> TriConsumer<T, U, V> unchecked(CheckedTriConsumer<T, U, V> checked) {
        return checked.unchecked();
    }

    public static <T, U, V> CheckedTriConsumer<T, U, V> checked(TriConsumer<T, U, V> unchecked) {
        return CheckedTriConsumer.checked(unchecked);
    }
}
