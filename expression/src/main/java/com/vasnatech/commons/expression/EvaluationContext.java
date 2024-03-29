package com.vasnatech.commons.expression;

public interface EvaluationContext {

    Object scope();

    default Object getProperty(String name) {
        return getProperty(scope(), name);
    }

    Object getProperty(Object parent, String name);

    void setProperty(Object parent, String name, Object value);

    default Object invokeMethod(String name, Object... parameters) {
        return invokeMethod(scope(), name, parameters);
    }

    Object invokeMethod(Object parent, String name, Object... parameters);

    Object invokeFunction(String name, Object... parameters);

    EvaluationContext createSubContext();
}
