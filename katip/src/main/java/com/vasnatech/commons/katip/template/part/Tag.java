package com.vasnatech.commons.katip.template.part;

import com.vasnatech.commons.katip.template.renderer.RenderContext;
import com.vasnatech.commons.katip.template.expression.Expression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tag implements Part {

    final TagRenderer renderer;
    final Map<String, Expression> attributes;
    final List<Part> children;

    public Tag(TagRenderer renderer) {
        this.renderer = renderer;
        this.attributes = new LinkedHashMap<>();
        this.children = new ArrayList<>();
    }

    public TagRenderer renderer() {
        return renderer;
    }

    public Map<String, Expression> attributes() {
        return attributes;
    }

    public void addAttribute(String name, Expression expression) {
        attributes.put(name, expression);
    }

    public List<Part> children() {
        return children;
    }

    public void addChild(Part child) {
        children.add(child);
    }

    @Override
    public void render(Appendable appendable, RenderContext renderContext) throws IOException {
        renderer.render(this, appendable, renderContext);
    }
}
