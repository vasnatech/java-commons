package com.vasnatech.commons.katip.template.part;

import com.vasnatech.commons.katip.template.renderer.RenderContext;

import java.io.IOException;

public interface TagRenderer {

    String name();

    boolean isContainer();

    default boolean isLeaf() {
        return !isContainer();
    }

    int minNumberOfParameters();

    int maxNumberOfParameters();

    void render(Tag tag, Appendable appendable, RenderContext renderContext) throws IOException;
}
