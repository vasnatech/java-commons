package com.vasnatech.commons.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ExceptionHandler implements Function<Throwable, Object>, Predicate<Throwable>, Consumer<Throwable> {

    private ExceptionHandler() {
    }

    @Override
    public Object apply(Throwable e) {
        toRuntimeException(e);
        return null;
    }

    @Override
    public boolean test(Throwable e) {
        toRuntimeException(e);
        return false;
    }

    @Override
    public void accept(Throwable e) {
        toRuntimeException(e);
    }

    public static <T extends RuntimeException> void toRuntimeException(final Throwable throwable) {
        throw  ExceptionHandler.<T, RuntimeException>eraseType(throwable);
    }

    @SuppressWarnings("unchecked")
    private static <R, T extends Throwable> R eraseType(final Throwable throwable) throws T {
        throw (T) throwable;
    }

    static ExceptionHandler INSTANCE = new ExceptionHandler();


    @SuppressWarnings("unchecked")
    public static <R, E extends Throwable> Function<E, R> asFunction() {
        return (Function<E, R>) INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> Predicate<E> asPredicate() {
        return (Predicate<E>) INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> Consumer<E> asConsumer() {
        return (Consumer<E>) INSTANCE;
    }
}