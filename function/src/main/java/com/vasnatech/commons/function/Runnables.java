package com.vasnatech.commons.function;

public class Runnables {

    private static final Runnable DO_NOTHING = () -> {};

    public static Runnable doNothing() {
        return DO_NOTHING;
    }

    public static Runnable of(Runnable runnable) {
        return runnable;
    }

    public static Runnable throwing(RuntimeException throwable) {
        return () -> { throw throwable; };
    }

    public static <E extends Throwable> Runnable unchecked(CheckedRunnable<E> checked) {
        return checked.unchecked();
    }

    public static <E extends Throwable> CheckedRunnable<E> checked(Runnable unchecked) {
        return CheckedRunnable.checked(unchecked);
    }
}
