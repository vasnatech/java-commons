package com.vasnatech.commons.type;

import java.util.LinkedHashMap;
import java.util.Map;

public class Scope {

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

    public Scope createSubScope() {
        return new Scope(this);
    }
}
