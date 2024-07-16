package com.vasnatech.commons.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Functions {

    public static <FIRST> Function<FIRST, FIRST> identity() {
        return Function.identity();
    }


    public static <FIRST, R> Function<FIRST, R> constant(R result) {
        return first -> result;
    }

    public static <FIRST, SECOND, R> BiFunction<FIRST, SECOND, R> constantBi(R result) {
        return (first, second) -> result;
    }

    public static <FIRST, SECOND, THIRD, R> TriFunction<FIRST, SECOND, THIRD, R> constantTri(R result) {
        return (first, second, third) -> result;
    }

    public static <FIRST, SECOND, THIRD, FOURTH, R> TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> constantTetra(R result) {
        return (first, second, third, fourth) -> result;
    }

    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> constantPenta(R result) {
        return (first, second, third, fourth, fifth) -> result;
    }


    public static <FIRST, R> Function<FIRST, R> of(Function<FIRST, R> function) {
        return function;
    }

    public static <FIRST, R> Function<FIRST, R> of(Supplier<R> supplier) {
        return value -> supplier.get();
    }

    public static <FIRST> Function<FIRST, Boolean> of(Predicate<FIRST> predicate) {
        return predicate::test;
    }


    private static final Function<?, Boolean> TAUTOLOGY = first -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST> Function<FIRST, Boolean> tautology() {
        return (Function<FIRST, Boolean>) TAUTOLOGY;
    }

    private static final Function<?, Boolean> CONTRADICTION = first -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST> Function<FIRST, Boolean> contradiction() {
        return (Function<FIRST, Boolean>)  CONTRADICTION;
    }

    private static final BiFunction<?, ?, Boolean> TAUTOLOGY_BI = (first, second) -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND> BiFunction<FIRST, SECOND, Boolean> tautologyBi() {
        return (BiFunction<FIRST, SECOND, Boolean>) TAUTOLOGY_BI;
    }

    private static final BiFunction<?, ?, Boolean> CONTRADICTION_BI = (first, second) -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND> BiFunction<FIRST, SECOND, Boolean> contradictionBi() {
        return (BiFunction<FIRST, SECOND, Boolean>) CONTRADICTION_BI;
    }

    private static final TriFunction<?, ?, ?, Boolean> TAUTOLOGY_TRI = (first, second, third) -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD> TriFunction<FIRST, SECOND, THIRD, Boolean> tautologyTri() {
        return (TriFunction<FIRST, SECOND, THIRD, Boolean>) TAUTOLOGY_TRI;
    }

    private static final TriFunction<?, ?, ?, Boolean> CONTRADICTION_TRI = (first, second, third) -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD> TriFunction<FIRST, SECOND, THIRD, Boolean> contradictionTri() {
        return (TriFunction<FIRST, SECOND, THIRD, Boolean>) CONTRADICTION_TRI;
    }

    private static final TetraFunction<?, ?, ?, ?, Boolean> TAUTOLOGY_TETRA = (first, second, third, fourth) -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH> TetraFunction<FIRST, SECOND, THIRD, FOURTH, Boolean> tautologyTetra() {
        return (TetraFunction<FIRST, SECOND, THIRD, FOURTH, Boolean>) TAUTOLOGY_TETRA;
    }

    private static final TetraFunction<?, ?, ?, ?, Boolean> CONTRADICTION_TETRA = (first, second, third, forth) -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH> TetraFunction<FIRST, SECOND, THIRD, FOURTH, Boolean> contradictionTetra() {
        return (TetraFunction<FIRST, SECOND, THIRD, FOURTH, Boolean>) CONTRADICTION_TETRA;
    }

    private static final PentaFunction<?, ?, ?, ?, ?, Boolean> TAUTOLOGY_PENTA = (first, second, third, fourth, fifth) -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, Boolean> tautologyPenta() {
        return (PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, Boolean>) TAUTOLOGY_PENTA;
    }

    private static final PentaFunction<?, ?, ?, ?, ?, Boolean> CONTRADICTION_PENTA = (first, second, third, forth, fifth) -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, Boolean> contradictionPenta() {
        return (PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, Boolean>) CONTRADICTION_PENTA;
    }


    public static <FIRST, R> Function<FIRST, R> unchecked(CheckedFunction<FIRST, R> checked) {
        return checked.unchecked();
    }

    public static <FIRST, R> CheckedFunction<FIRST, R> checked(Function<FIRST, R> unchecked) {
        return CheckedFunction.checked(unchecked);
    }

    public static <FIRST, SECOND, R> BiFunction<FIRST, SECOND, R> unchecked(CheckedBiFunction<FIRST, SECOND, R> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, R> CheckedBiFunction<FIRST, SECOND, R> checked(BiFunction<FIRST, SECOND, R> unchecked) {
        return CheckedBiFunction.checked(unchecked);
    }

    public static <FIRST, SECOND, THIRD, R> TriFunction<FIRST, SECOND, THIRD, R> unchecked(CheckedTriFunction<FIRST, SECOND, THIRD, R> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, THIRD, R> CheckedTriFunction<FIRST, SECOND, THIRD, R> checked(TriFunction<FIRST, SECOND, THIRD, R> unchecked) {
        return CheckedTriFunction.checked(unchecked);
    }

    public static <FIRST, SECOND, THIRD, FOURTH, R> TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked(CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, THIRD, FOURTH, R> CheckedTetraFunction<FIRST, SECOND, THIRD, FOURTH, R> checked(TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> unchecked) {
        return CheckedTetraFunction.checked(unchecked);
    }

    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked(CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R> CheckedPentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> checked(PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> unchecked) {
        return CheckedPentaFunction.checked(unchecked);
    }
}