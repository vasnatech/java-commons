package com.vasnatech.commons.katip.template.part;

import com.vasnatech.commons.katip.template.expression.Expression;
import com.vasnatech.commons.katip.template.renderer.RenderContext;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Foreach extends ContainerRenderer {

    @Override
    public String name() {
        return "foreach";
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
        Expression iteratorExpr = tag.expressions().get(1);
        Object iteratorSource = iteratorExpr.get(renderContext);
        Iterator<?> iterator;
        if (iteratorSource == null) {
            iterator = Collections.emptyIterator();
        } else if (iteratorSource instanceof Iterator) {
            iterator = (Iterator<?>)iteratorSource;
        } else if (iteratorSource instanceof Iterable) {
            iterator = ((Iterable<?>)iteratorSource).iterator();
        } else if (iteratorSource.getClass().isArray()) {
            iterator = List.of(((Object[])iteratorSource)).iterator();
        } else {
            iterator = Collections.emptyIterator();
        }

        while (iterator.hasNext()) {
            RenderContext subRendererContext = renderContext.createSubContext();
            variableExpr.set(iterator.next(), subRendererContext);
            renderChildren(tag, appendable, subRendererContext);
        }
    }
}
