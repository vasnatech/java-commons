package com.vasnatech.commons.function;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Operators {

    public static <T> Operator<T> identity() {
        return Operator.identity();
    }


    public static <T> Operator<T> constant(T result) {
        return first -> result;
    }

    public static <T> BinaryOperator<T> constantBi(T result) {
        return (first, second) -> result;
    }

    public static <T> TriOperator<T> constantTri(T result) {
        return (first, second, third) -> result;
    }

    public static <T> TetraOperator<T> constantTetra(T result) {
        return (first, second, third, fourth) -> result;
    }

    public static <T> PentaOperator<T> constantPenta(T result) {
        return (first, second, third, fourth, fifth) -> result;
    }


    public static <T> Operator<T> of(Operator<T> operator) {
        return operator;
    }

    public static <T> Operator<T> of(Function<T, T> function) {
        return function::apply;
    }

    public static <T> Operator<T> of(Supplier<T> supplier) {
        return value -> supplier.get();
    }

    public static Operator<Boolean> of(Predicate<Boolean> predicate) {
        return predicate::test;
    }


    public static <T> Operator<T> unchecked(CheckedOperator<T> checked) {
        return checked.unchecked();
    }

    public static <T> BinaryOperator<T> unchecked(CheckedBiOperator<T> checked) {
        return checked.unchecked();
    }

    public static <T> TriOperator<T> unchecked(CheckedTriOperator<T> checked) {
        return checked.unchecked();
    }

    public static <T> TetraOperator<T> unchecked(CheckedTetraOperator<T> checked) {
        return checked.unchecked();
    }

    public static <T> PentaOperator<T> unchecked(CheckedPentaOperator<T> checked) {
        return checked.unchecked();
    }


    public static <T> CheckedOperator<T> checked(Operator<T> unchecked) {
        return CheckedOperator.checked(unchecked);
    }

    public static <T> CheckedBiOperator<T> checked(BinaryOperator<T> unchecked) {
        return CheckedBiOperator.checked(unchecked);
    }

    public static <T> CheckedTriOperator<T> checked(TriOperator<T> unchecked) {
        return CheckedTriOperator.checked(unchecked);
    }

    public static <T> CheckedTetraOperator<T> checked(TetraOperator<T> unchecked) {
        return CheckedTetraOperator.checked(unchecked);
    }

    public static <T> CheckedPentaOperator<T> checked(PentaOperator<T> unchecked) {
        return CheckedPentaOperator.checked(unchecked);
    }
}