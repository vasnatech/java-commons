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


    public static <T, E extends Throwable> Operator<T> unchecked(CheckedOperator<T, E> checked) {
        return checked.unchecked();
    }

    public static <T, E extends Throwable> BinaryOperator<T> unchecked(CheckedBiOperator<T, E> checked) {
        return checked.unchecked();
    }

    public static <T, E extends Throwable> TriOperator<T> unchecked(CheckedTriOperator<T, E> checked) {
        return checked.unchecked();
    }

    public static <T, E extends Throwable> TetraOperator<T> unchecked(CheckedTetraOperator<T, E> checked) {
        return checked.unchecked();
    }

    public static <T, E extends Throwable> PentaOperator<T> unchecked(CheckedPentaOperator<T, E> checked) {
        return checked.unchecked();
    }


    public static <T, E extends Throwable> CheckedOperator<T, E> checked(Operator<T> unchecked) {
        return CheckedOperator.checked(unchecked);
    }

    public static <T, E extends Throwable> CheckedBiOperator<T, E> checked(BinaryOperator<T> unchecked) {
        return CheckedBiOperator.checked(unchecked);
    }

    public static <T, E extends Throwable> CheckedTriOperator<T, E> checked(TriOperator<T> unchecked) {
        return CheckedTriOperator.checked(unchecked);
    }

    public static <T, E extends Throwable> CheckedTetraOperator<T, E> checked(TetraOperator<T> unchecked) {
        return CheckedTetraOperator.checked(unchecked);
    }

    public static <T, E extends Throwable> CheckedPentaOperator<T, E> checked(PentaOperator<T> unchecked) {
        return CheckedPentaOperator.checked(unchecked);
    }
}