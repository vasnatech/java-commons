package com.vasnatech.commons.function;

import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedRunnable {

    void run() throws Exception;

    default Runnable unchecked() {
        return unchecked(this, ExceptionHandler.asConsumer());
    }

    default Runnable unchecked(Consumer<Exception> exceptionHandler) {
        return unchecked(this, exceptionHandler);
    }

    static Runnable unchecked(CheckedRunnable checked) {
        return unchecked(checked, ExceptionHandler.asConsumer());
    }

    static CheckedRunnable checked(Runnable runnable) {
        return runnable::run;
    }

    static Runnable unchecked(CheckedRunnable checked, Consumer<Exception> exceptionHandler) {
        return () -> {
            try {
                checked.run();
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
}
