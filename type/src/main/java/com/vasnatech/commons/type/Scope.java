package com.vasnatech.commons.type;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Scope implements VariableContainer {

    final Scope upperScope;
    final Map<String, Object> variables;

    public Scope(Map<String, Object> variables) {
        this.upperScope = null;
        this.variables = new LinkedHashMap<>(variables);
    }

    public Scope(Scope upperScope) {
        this.upperScope = upperScope;
        this.variables = new LinkedHashMap<>();
    }

    public boolean containsKey(String name) {
        Scope scope = this;
        while (scope != null) {
            if (scope.variables.containsKey(name)) {
                return true;
            }
            scope = scope.upperScope;
        }
        return false;
    }

    public Object get(String name) {
        Scope scope = this;
        while (scope != null) {
            if (scope.variables.containsKey(name)) {
                return scope.variables.get(name);
            }
            scope = scope.upperScope;
        }
        return null;
    }

    public Object put(String name, Object value) {
        Scope scope = this;
        while (scope != null) {
            if (scope.variables.containsKey(name)) {
                return scope.variables.put(name, value);
            }
            scope = scope.upperScope;
        }
        return variables.put(name, value);
    }

    public Object remove(String name) {
        Scope scope = this;
        while (scope != null) {
            if (scope.variables.containsKey(name)) {
                return scope.variables.remove(name);
            }
            scope = scope.upperScope;
        }
        return null;
    }

    public Scope createSubScope() {
        return new Scope(this);
    }

    public Scope flatten() {
        return new Scope(flattenAsMap());
    }

    public Map<String, Object> flattenAsMap() {
        Map<String, Object> all = new TreeMap<>();
        Scope scope = this;
        while (scope != null) {
            Map<String, Object> map = scope.variables;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                all.putIfAbsent(entry.getKey(), entry.getValue());
            }
            scope = scope.upperScope;
        }
        return all;
    }
}
