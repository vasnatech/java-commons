package com.vasnatech.commons.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Functions {

    public static <T> Function<T, T> identity() {
        return Function.identity();
    }

    public static <T, R> Function<T, R> constant(R result) {
        return t -> result;
    }

    public static <T, U, R> BiFunction<T, U, R> constantBi(R result) {
        return (t, u) -> result;
    }

    public static <T, U, V, R> TriFunction<T, U, V, R> constantTri(R result) {
        return (t, u, v) -> result;
    }

    public static <T, R> Function<T, R> of(Function<T, R> function) {
        return function;
    }

    public static <T, R> Function<T, R> of(Supplier<R> supplier) {
        return value -> supplier.get();
    }

    public static <T> Function<T, Boolean> of(Predicate<T> predicate) {
        return predicate::test;
    }

    private static final Function<?, Boolean> TAUTOLOGY = t -> true;

    @SuppressWarnings("unchecked")
    public static <T> Function<T, Boolean> tautology() {
        return (Function<T, Boolean>) TAUTOLOGY;
    }

    private static final Function<?, Boolean> CONTRADICTION = t -> false;

    @SuppressWarnings("unchecked")
    public static <T> Function<T, Boolean> contradiction() {
        return (Function<T, Boolean>)  CONTRADICTION;
    }

    private static final BiFunction<?, ?, Boolean> TAUTOLOGY_BI = (t, u) -> true;

    @SuppressWarnings("unchecked")
    public static <T, U> BiFunction<T, U, Boolean> tautologyBi() {
        return (BiFunction<T, U, Boolean>) TAUTOLOGY_BI;
    }

    private static final BiFunction<?, ?, Boolean> CONTRADICTION_BI = (t, u) -> false;

    @SuppressWarnings("unchecked")
    public static <T, U> BiFunction<T, U, Boolean> contradictionBi() {
        return (BiFunction<T, U, Boolean>) CONTRADICTION_BI;
    }

    private static final TriFunction<?, ?, ?, Boolean> TAUTOLOGY_TRI = (t, u, v) -> true;

    @SuppressWarnings("unchecked")
    public static <T, U, V> TriFunction<T, U, V, Boolean> tautologyTri() {
        return (TriFunction<T, U, V, Boolean>) TAUTOLOGY_TRI;
    }

    private static final TriFunction<?, ?, ?, Boolean> CONTRADICTION_TRI = (t, u, v) -> false;

    @SuppressWarnings("unchecked")
    public static <T, U, V> TriFunction<T, U, V, Boolean> contradictionTri() {
        return (TriFunction<T, U, V, Boolean>) CONTRADICTION_TRI;
    }

    public static <T, R> Function<T, R> unchecked(CheckedFunction<T, R> checked) {
        return checked.unchecked();
    }

    public static <T, R> CheckedFunction<T, R> checked(Function<T, R> unchecked) {
        return CheckedFunction.checked(unchecked);
    }

    public static <T, U, R> BiFunction<T, U, R> unchecked(CheckedBiFunction<T, U, R> checked) {
        return checked.unchecked();
    }

    public static <T, U, R> CheckedBiFunction<T, U, R> checked(BiFunction<T, U, R> unchecked) {
        return CheckedBiFunction.checked(unchecked);
    }

    public static <T, U, V, R> TriFunction<T, U, V, R> unchecked(CheckedTriFunction<T, U, V, R> checked) {
        return checked.unchecked();
    }

    public static <T, U, V, R> CheckedTriFunction<T, U, V, R> checked(TriFunction<T, U, V, R> unchecked) {
        return CheckedTriFunction.checked(unchecked);
    }
}