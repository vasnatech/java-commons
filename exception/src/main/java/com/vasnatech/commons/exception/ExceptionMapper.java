package com.vasnatech.commons.exception;

public interface ExceptionMapper<E extends Throwable, T> {

    T map(E exception);
}
