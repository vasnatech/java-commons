package com.vasnatech.commons.katip.template.part;

import com.vasnatech.commons.katip.template.renderer.RenderContext;
import com.vasnatech.commons.katip.template.expression.Expression;

import java.io.IOException;

public class Get implements TagRenderer {

    @Override
    public String name() {
        return "get";
    }

    @Override
    public boolean isContainer() {
        return false;
    }

    @Override
    public int minNumberOfParameters() {
        return 1;
    }

    @Override
    public int maxNumberOfParameters() {
        return 1;
    }

    @Override
    public void render(Tag tag, Appendable appendable, RenderContext renderContext) throws IOException {
        Expression valueExpr = tag.expressions().get(0);
        Object value = valueExpr.get(renderContext);
        if (value != null) {
            appendable.append(value.toString());
        }
    }
}
