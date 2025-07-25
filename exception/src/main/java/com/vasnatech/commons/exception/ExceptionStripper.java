package com.vasnatech.commons.exception;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExceptionStripper {

    private final Set<Class<? extends Throwable>> stripClasses;

    public ExceptionStripper(Set<Class<? extends Throwable>> stripClasses) {
        this.stripClasses = stripClasses;
    }

    @SafeVarargs
    public ExceptionStripper(Class<? extends Throwable>... stripClasses) {
        this(Stream.of(stripClasses).collect(Collectors.toSet()));
    }

    public Throwable strip(Throwable ex) {
        if (stripClasses.contains(ex.getClass())) {
            strip(ex.getCause());
        }
        for (Class<? extends Throwable> exceptionClass : stripClasses) {
            if (exceptionClass.isInstance(ex)) {
                return strip(ex.getCause());
            }
        }
        return ex;
    }
}
