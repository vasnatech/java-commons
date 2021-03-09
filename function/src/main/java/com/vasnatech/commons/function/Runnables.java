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

    public static Runnable unchecked(CheckedRunnable checked) {
        return checked.unchecked();
    }

    public static CheckedRunnable checked(Runnable unchecked) {
        return CheckedRunnable.checked(unchecked);
    }
}
