package com.vasnatech.commons.katip.template.renderer;

import com.vasnatech.commons.katip.template.Scope;
import com.vasnatech.commons.katip.template.expression.function.PureFunction;
import com.vasnatech.commons.katip.template.expression.function.PureFunctions;
import com.vasnatech.commons.reflection.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class DefaultRenderContext implements RenderContext {

    Scope scope;

    public DefaultRenderContext(Map<String, Object> variables) {
        this(new Scope(variables));
    }

    DefaultRenderContext(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Object scope() {
        return scope;
    }

    @Override
    public Object getProperty(Object parent, String name) {
        if (parent == null || StringUtils.isEmpty(name)) {
            return null;
        }

        if (parent instanceof Scope) {
            Scope parentScope = (Scope)parent;
            if (parentScope.containsKey(name)) {
                return parentScope.get(name);
            }
        }

        if (parent instanceof Map) {
            Map<?, ?> parentMap = (Map<?, ?>)parent;
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
        if (parent == null || StringUtils.isEmpty(name)) {
            return;
        }

        if (parent instanceof Scope) {
            Scope parentScope = (Scope)parent;
            parentScope.put(name, value);
        }

        if (parent instanceof Map) {
            Map<String, Object> parentMap = (Map<String, Object>)parent;
            parentMap.put(name, value);
        }

        try {
            ReflectionUtil.setProperty(parent, name, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
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

    public RenderContext createSubContext() {
        return new DefaultRenderContext(new Scope(this.scope));
    }
}
