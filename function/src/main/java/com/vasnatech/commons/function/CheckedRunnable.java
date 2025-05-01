package com.vasnatech.commons.function;

import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedRunnable<E extends Throwable> {

    void run() throws E;

    default Runnable unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default Runnable unchecked(Consumer<E> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static <E extends Throwable> Runnable unchecked(CheckedRunnable<E> checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static <E extends Throwable> CheckedRunnable<E> checked(Runnable runnable) {
        return runnable::run;
    }

    static <E extends Throwable> Runnable unchecked(CheckedRunnable<E> checked, Consumer<E> exceptionHandler) {
        return () -> run(checked, exceptionHandler);
    }

    @SuppressWarnings("unchecked")
    static <E extends Throwable> void run(CheckedRunnable<E> checked, Consumer<E> exceptionHandler) {
        try {
            checked.run();
        } catch (Throwable e) {
            exceptionHandler.accept((E)e);
        }
    }

    static <E extends Throwable> void run(CheckedRunnable<E> checked) {
        run(checked, ExceptionHandler.asConsumer());
    }
}
