package com.vasnatech.commons.expression;

public class ChainedExpression implements ChainableExpression {

    final Expression parentExpression;
    final ChainableExpression childExpression;

    public ChainedExpression(Expression parentExpression, ChainableExpression childExpression) {
        this.parentExpression = parentExpression;
        this.childExpression = childExpression;
    }

    @Override
    public Object get(Object parent, EvaluationContext evaluationContext) {
        Object newParent = parentExpression == null ? parent : parentExpression.get(parent, evaluationContext);
        return childExpression.get(newParent, evaluationContext);
    }

    @Override
    public void set(Object parent, Object value, EvaluationContext evaluationContext) {
        Object newParent = parentExpression == null ? parent : parentExpression.get(parent, evaluationContext);
        childExpression.set(newParent, value, evaluationContext);
    }

    @Override
    public String toString() {
        return (parentExpression == null ? "" : parentExpression + ".") + childExpression;
    }
}
