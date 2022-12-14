package com.vasnatech.commons.katip.template.part;

import com.vasnatech.commons.katip.template.renderer.RenderContext;
import com.vasnatech.commons.katip.template.expression.Expression;

import java.io.IOException;

public class If extends ContainerRenderer {

    @Override
    public String name() {
        return "if";
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
        Expression condition = tag.expressions().get(0);
        Object value = condition.get(renderContext);

        if ( (value != null) && (!(value instanceof Boolean) || (Boolean)value) ) {
            renderChildren(tag, appendable, renderContext.createSubContext());
        }
    }
}
