package com.vasnatech.commons.katip.template.expression.function;

public class LessThan implements ComparisonFunction {

    @Override
    public String name() {
        return "lt";
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Boolean invoke(Object[] params) {
        return ((Comparable)params[0]).compareTo(params[1]) < 0;
    }
}
