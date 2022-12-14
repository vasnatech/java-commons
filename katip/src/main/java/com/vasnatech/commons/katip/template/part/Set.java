package com.vasnatech.commons.katip.template.part;

import com.vasnatech.commons.katip.template.expression.Expression;
import com.vasnatech.commons.katip.template.renderer.RenderContext;

import java.io.IOException;

public class Set implements TagRenderer {

    @Override
    public String name() {
        return "set";
    }

    @Override
    public boolean isContainer() {
        return false;
    }

    @Override
    public int minNumberOfParameters() {
        return 2;
    }

    @Override
    public int maxNumberOfParameters() {
        return 2;
    }

    @Override
    public void render(Tag tag, Appendable appendable, RenderContext renderContext) throws IOException {
        Expression variableExpr = tag.expressions().get(0);
        Expression valueExpr = tag.expressions().get(1);
        Object value = valueExpr.get(renderContext);
        variableExpr.set(value, renderContext);
    }
}
