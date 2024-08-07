package com.vasnatech.commons.function;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Predicates {

    public static <FIRST> Predicate<FIRST> of(Predicate<FIRST> predicate) {
        return predicate;
    }

    public static <FIRST> Predicate<FIRST> of(Function<FIRST, Boolean> function) {
        return function::apply;
    }

    public static <FIRST> Predicate<FIRST> of(Supplier<Boolean> supplier) {
        return value -> supplier.get();
    }

    private static final Predicate<?> TAUTOLOGY = first -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST> Predicate<FIRST> tautology() {
        return (Predicate<FIRST>) TAUTOLOGY;
    }

    private static final Predicate<?> CONTRADICTION = first -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST> Predicate<FIRST> contradiction() {
        return (Predicate<FIRST>) CONTRADICTION;
    }

    private static final BiPredicate<?, ?> TAUTOLOGY_BI = (first, second) -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND> BiPredicate<FIRST, SECOND> tautologyBi() {
        return (BiPredicate<FIRST, SECOND>) TAUTOLOGY_BI;
    }

    private static final BiPredicate<?, ?> CONTRADICTION_BI = (first, second) -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND> BiPredicate<FIRST, SECOND> contradictionBi() {
        return (BiPredicate<FIRST, SECOND>) CONTRADICTION_BI;
    }

    private static final TriPredicate<?, ?, ?> TAUTOLOGY_TRI = (first, second, third) -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD> TriPredicate<FIRST, SECOND, THIRD> tautologyTri() {
        return (TriPredicate<FIRST, SECOND, THIRD>) TAUTOLOGY_TRI;
    }

    private static final TriPredicate<?, ?, ?> CONTRADICTION_TRI = (first, second, third) -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD> TriPredicate<FIRST, SECOND, THIRD> contradictionTri() {
        return (TriPredicate<FIRST, SECOND, THIRD>) CONTRADICTION_TRI;
    }

    private static final TetraPredicate<?, ?, ?, ?> TAUTOLOGY_TETRA = (first, second, third, fourth) -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH> TetraPredicate<FIRST, SECOND, THIRD, FOURTH> tautologyTetra() {
        return (TetraPredicate<FIRST, SECOND, THIRD, FOURTH>) TAUTOLOGY_TETRA;
    }

    private static final TetraPredicate<?, ?, ?, ?> CONTRADICTION_TETRA = (first, second, third, fourth) -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH> TetraPredicate<FIRST, SECOND, THIRD, FOURTH> contradictionTetra() {
        return (TetraPredicate<FIRST, SECOND, THIRD, FOURTH>) CONTRADICTION_TETRA;
    }

    private static final PentaPredicate<?, ?, ?, ?, ?> TAUTOLOGY_PENTA = (first, second, third, fourth, fifth) -> true;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> tautologyPenta() {
        return (PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH>) TAUTOLOGY_PENTA;
    }

    private static final PentaPredicate<?, ?, ?, ?, ?> CONTRADICTION_PENTA = (first, second, third, fourth, fifth) -> false;

    @SuppressWarnings("unchecked")
    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> contradictionPenta() {
        return (PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH>) CONTRADICTION_PENTA;
    }


    public static <FIRST> Predicate<FIRST> unchecked(CheckedPredicate<FIRST> checked) {
        return checked.unchecked();
    }

    public static <FIRST> CheckedPredicate<FIRST> checked(Predicate<FIRST> unchecked) {
        return CheckedPredicate.checked(unchecked);
    }

    public static <FIRST, SECOND> BiPredicate<FIRST, SECOND> unchecked(CheckedBiPredicate<FIRST, SECOND> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND> CheckedBiPredicate<FIRST, SECOND> checked(BiPredicate<FIRST, SECOND> unchecked) {
        return CheckedBiPredicate.checked(unchecked);
    }

    public static <FIRST, SECOND, THIRD> TriPredicate<FIRST, SECOND, THIRD> unchecked(CheckedTriPredicate<FIRST, SECOND, THIRD> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, THIRD> CheckedTriPredicate<FIRST, SECOND, THIRD> checked(TriPredicate<FIRST, SECOND, THIRD> unchecked) {
        return CheckedTriPredicate.checked(unchecked);
    }

    public static <FIRST, SECOND, THIRD, FOURTH> TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked(CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, THIRD, FOURTH> CheckedTetraPredicate<FIRST, SECOND, THIRD, FOURTH> checked(TetraPredicate<FIRST, SECOND, THIRD, FOURTH> unchecked) {
        return CheckedTetraPredicate.checked(unchecked);
    }

    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked(CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked) {
        return checked.unchecked();
    }

    public static <FIRST, SECOND, THIRD, FOURTH, FIFTH> CheckedPentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> checked(PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> unchecked) {
        return CheckedPentaPredicate.checked(unchecked);
    }
}