package com.vasnatech.commons.katip.template.expression.function;

public class GreaterThan implements ComparisonFunction {

    @Override
    public String name() {
        return "gt";
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Boolean invoke(Object[] params) {
        return ((Comparable)params[0]).compareTo(params[1]) > 0;
    }
}
