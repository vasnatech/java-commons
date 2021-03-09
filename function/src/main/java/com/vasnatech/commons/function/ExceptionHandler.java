package com.vasnatech.commons.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ExceptionHandler implements Function<Exception, Object>, Predicate<Exception>, Consumer<Exception> {

    private ExceptionHandler() {
    }

    @Override
    public Object apply(Exception e) {
        toRuntimeException(e);
        return null;
    }

    @Override
    public boolean test(Exception e) {
        toRuntimeException(e);
        return false;
    }

    @Override
    public void accept(Exception e) {
        toRuntimeException(e);
    }

    private void toRuntimeException(Exception e) {
        if (e instanceof RuntimeException)
            throw (RuntimeException) e;
        throw new RuntimeException(e);
    }

    static ExceptionHandler INSTANCE = new ExceptionHandler();

    @SuppressWarnings("unchecked")
    public static <R> Function<Exception, R> asFunction() {
        return (Function<Exception, R>) INSTANCE;
    }

    public static Predicate<Exception> asPredicate() {
        return INSTANCE;
    }

    public static Consumer<Exception> asConsumer() {
        return INSTANCE;
    }

}