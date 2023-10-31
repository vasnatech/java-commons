package com.vasnatech.commons.expression;

public interface MemberExpression extends ChainableExpression {

    String name();

    Object get(Object parent, EvaluationContext evaluationContext);
}
