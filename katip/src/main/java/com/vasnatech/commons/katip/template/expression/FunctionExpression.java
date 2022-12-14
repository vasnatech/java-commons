package com.vasnatech.commons.katip.template.expression;

import com.vasnatech.commons.katip.template.renderer.RenderContext;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionExpression implements MemberExpression {

    final String name;
    final List<Expression> parameters;

    public FunctionExpression(String name, List<Expression> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object get(Object parent, RenderContext renderContext) {
        if (parent == null) {
            return renderContext.invokeFunction(
                    name,
                    parameters.stream().map(expression -> expression.get(renderContext)).toArray()
            );
        }
        return renderContext.invokeMethod(
                parent,
                name,
                parameters.stream().map(expression -> expression.get(renderContext)).toArray()
        );
    }

    @Override
    public void set(Object parent, Object value, RenderContext renderContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return parameters.stream().map(String::valueOf).collect(Collectors.joining(", ", name + "(", ")"));
    }
}
