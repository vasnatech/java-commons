package com.vasnatech.commons.katip.template.part;

import com.vasnatech.commons.katip.template.renderer.RenderContext;

import java.io.IOException;

public abstract class ContainerRenderer implements TagRenderer {

    @Override
    public boolean isContainer() {
        return true;
    }

    protected void renderChildren(Tag tag, Appendable appendable, RenderContext renderContext) throws IOException {
        for (Part child : tag.children()) {
            child.render(appendable, renderContext);
        }
    }
}
