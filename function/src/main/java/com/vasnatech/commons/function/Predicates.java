package com.vasnatech.commons.function;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Predicates {

    public static <T> Predicate<T> of(Predicate<T> predicate) {
        return predicate;
    }

    public static <T> Predicate<T> of(Function<T, Boolean> function) {
        return function::apply;
    }

    public static <T> Predicate<T> of(Supplier<Boolean> supplier) {
        return value -> supplier.get();
    }

    private static final Predicate<?> TAUTOLOGY = t -> true;

    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> tautology() {
        return (Predicate<T>) TAUTOLOGY;
    }

    private static final Predicate<?> CONTRADICTION = t -> false;

    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> contradiction() {
        return (Predicate<T>) CONTRADICTION;
    }

    private static final BiPredicate<?, ?> TAUTOLOGY_BI = (t, u) -> true;

    @SuppressWarnings("unchecked")
    public static <T, U> BiPredicate<T, U> tautologyBi() {
        return (BiPredicate<T, U>) TAUTOLOGY_BI;
    }

    private static final BiPredicate<?, ?> CONTRADICTION_BI = (t, u) -> false;

    @SuppressWarnings("unchecked")
    public static <T, U> BiPredicate<T, U> contradictionBi() {
        return (BiPredicate<T, U>) CONTRADICTION_BI;
    }

    private static final TriPredicate<?, ?, ?> TAUTOLOGY_TRI = (t, u, v) -> true;

    @SuppressWarnings("unchecked")
    public static <T, U, V> TriPredicate<T, U, V> tautologyTri() {
        return (TriPredicate<T, U, V>) TAUTOLOGY_TRI;
    }

    private static final TriPredicate<?, ?, ?> CONTRADICTION_TRI = (t, u, v) -> false;

    @SuppressWarnings("unchecked")
    public static <T, U, V> TriPredicate<T, U, V> contradictionTri() {
        return (TriPredicate<T, U, V>) CONTRADICTION_TRI;
    }

    public static <T> Predicate<T> unchecked(CheckedPredicate<T> checked) {
        return checked.unchecked();
    }

    public static <T> CheckedPredicate<T> checked(Predicate<T> unchecked) {
        return CheckedPredicate.checked(unchecked);
    }

    public static <T, U> BiPredicate<T, U> unchecked(CheckedBiPredicate<T, U> checked) {
        return checked.unchecked();
    }

    public static <T, U> CheckedBiPredicate<T, U> checked(BiPredicate<T, U> unchecked) {
        return CheckedBiPredicate.checked(unchecked);
    }

    public static <T, U, V> TriPredicate<T, U, V> unchecked(CheckedTriPredicate<T, U, V> checked) {
        return checked.unchecked();
    }

    public static <T, U, V> CheckedTriPredicate<T, U, V> checked(TriPredicate<T, U, V> unchecked) {
        return CheckedTriPredicate.checked(unchecked);
    }
}