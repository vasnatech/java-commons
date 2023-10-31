package com.vasnatech.commons.expression;

public class ConstantExpression implements Expression {

    final Object value;

    public ConstantExpression(Object value) {
        this.value = value;
    }

    @Override
    public Object get(Object parent, EvaluationContext evaluationContext) {
        return value;
    }

    @Override
    public void set(Object parent, Object value, EvaluationContext evaluationContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        if (value instanceof String str) {
            return "'" + str + "'";
        }
        return String.valueOf(value);
    }
}
