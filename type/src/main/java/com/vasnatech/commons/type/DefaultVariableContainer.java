package com.vasnatech.commons.type;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultVariableContainer implements VariableContainer {

    final Map<String, Object> variables;

    public DefaultVariableContainer() {
        this.variables = new LinkedHashMap<>();
    }

    public DefaultVariableContainer(Map<String, Object> variables) {
        this.variables = new LinkedHashMap<>(variables);
    }

    @Override
    public boolean containsKey(String name) {
        return variables.containsKey(name);
    }

    @Override
    public Object get(String name) {
        return variables.get(name);
    }

    @Override
    public Object put(String name, Object value) {
        return variables.put(name, value);
    }

    @Override
    public Object remove(String name) {
        return variables.remove(name);
    }

    @Override
    public Map<String, Object> flattenAsMap() {
        return Map.copyOf(variables);
    }
}
