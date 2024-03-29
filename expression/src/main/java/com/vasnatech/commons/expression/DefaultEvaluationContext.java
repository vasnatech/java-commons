package com.vasnatech.commons.expression;

import com.vasnatech.commons.expression.function.PureFunction;
import com.vasnatech.commons.expression.function.PureFunctions;
import com.vasnatech.commons.reflection.ReflectionUtil;
import com.vasnatech.commons.type.Scope;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class DefaultEvaluationContext implements EvaluationContext {

    protected Scope scope;

    public DefaultEvaluationContext(Map<String, Object> variables) {
        this(new Scope(variables));
    }

    public DefaultEvaluationContext(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Scope scope() {
        return scope;
    }

    @Override
    public Object getProperty(Object parent, String name) {
        if (parent == null || StringUtils.isEmpty(name)) {
            return null;
        }

        if (parent instanceof Scope parentScope) {
            if (parentScope.containsKey(name)) {
                return parentScope.get(name);
            }
        }

        if (parent instanceof Map<?, ?> parentMap) {
            if (parentMap.containsKey(name)) {
                return parentMap.get(name);
            }
        }

        try {
            return ReflectionUtil.getProperty(parent, name);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setProperty(Object parent, String name, Object value) {
        if (parent != null && StringUtils.isNotEmpty(name)) {
            if (parent instanceof Scope parentScope) {
                parentScope.put(name, value);
            } else if (parent instanceof Map) {
                Map<String, Object> parentMap = (Map<String, Object>)parent;
                parentMap.put(name, value);
            } else {
                try {
                    ReflectionUtil.setProperty(parent, name, value);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                }
            }
        }
    }

    @Override
    public Object invokeMethod(Object parent, String name, Object... parameters) {
        try {
            return ReflectionUtil.invokeMethod(parent, name, parameters);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public Object invokeFunction(String name, Object... parameters) {
        PureFunction function = PureFunctions.get(name);
        if (function == null) {
            return null;
        }
        try {
            return function.invoke(parameters);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public EvaluationContext createSubContext() {
        return new DefaultEvaluationContext(this.scope.createSubScope());
    }
}
