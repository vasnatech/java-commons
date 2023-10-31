package com.vasnatech.commons.expression;

public record PropertyExpression(String name) implements MemberExpression {

    @Override
    public Object get(Object parent, EvaluationContext evaluationContext) {
        return evaluationContext.getProperty(parent, name);
    }

    @Override
    public void set(Object parent, Object value, EvaluationContext evaluationContext) {
        evaluationContext.setProperty(parent, name, value);
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }
}
