package com.vasnatech.commons.katip.template.expression;

import com.vasnatech.commons.katip.template.Node;
import com.vasnatech.commons.katip.template.renderer.RenderContext;

public interface Expression extends Node {

    default Object get(RenderContext renderContext) {
        return get(renderContext.scope(), renderContext);
    }

    Object get(Object parent, RenderContext renderContext);

    default void set(Object value, RenderContext renderContext) {
        set(renderContext.scope(), value, renderContext);
    }

    void set(Object parent, Object value, RenderContext renderContext);
}
