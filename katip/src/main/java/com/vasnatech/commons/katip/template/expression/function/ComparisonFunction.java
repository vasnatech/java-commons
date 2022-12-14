package com.vasnatech.commons.katip.template.expression.function;

public interface ComparisonFunction extends PureFunction {

    @Override
    default Class<?> returnType() {
        return Boolean.class;
    }

    @Override
    default int minNumberOfParameters() {
        return 2;
    }

    @Override
    default int maxNumberOfParameters() {
        return 2;
    }

    @Override
    default Class<?> parameterType(int index) {
        return Comparable.class;
    }
}
