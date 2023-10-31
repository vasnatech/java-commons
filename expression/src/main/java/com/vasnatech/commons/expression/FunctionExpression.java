package com.vasnatech.commons.expression;

import com.vasnatech.commons.type.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionExpression implements MemberExpression {

    final String name;
    final List<Expression> parameters;

    public FunctionExpression(String name) {
        this(name, new ArrayList<>(2));
    }

    public FunctionExpression(String name, List<Expression> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public void addParameter(Expression expression) {
        parameters.add(expression);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object get(Object parent, EvaluationContext evaluationContext) {
        if (parent == null || parent instanceof Scope) {
            return evaluationContext.invokeFunction(
                    name,
                    parameters.stream().map(expression -> expression.get(evaluationContext)).toArray()
            );
        }
        return evaluationContext.invokeMethod(
                parent,
                name,
                parameters.stream().map(expression -> expression.get(evaluationContext)).toArray()
        );
    }

    @Override
    public void set(Object parent, Object value, EvaluationContext evaluationContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return parameters.stream().map(String::valueOf).collect(Collectors.joining(",", name + "(", ")"));
    }
}
