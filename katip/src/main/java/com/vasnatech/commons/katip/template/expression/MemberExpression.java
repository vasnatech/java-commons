package com.vasnatech.commons.katip.template.expression;

import com.vasnatech.commons.katip.template.renderer.RenderContext;

public interface MemberExpression extends ChainableExpression {

    String name();

    Object get(Object parent, RenderContext renderContext);
}
