package com.vasnatech.commons.expression;

public interface Expression {

    default Object get(EvaluationContext evaluationContext) {
        return get(evaluationContext.scope(), evaluationContext);
    }

    Object get(Object parent, EvaluationContext evaluationContext);

    default void set(Object value, EvaluationContext evaluationContext) {
        set(evaluationContext.scope(), value, evaluationContext);
    }

    void set(Object parent, Object value, EvaluationContext renderContext);
}
