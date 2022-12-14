package com.vasnatech.commons.katip.template.expression.function;

public class And implements BinaryBooleanFunction {

    @Override
    public String name() {
        return "and";
    }

    @Override
    public Boolean invokeBoolean(Boolean left, Boolean right) {
        return left && right;
    }
}
