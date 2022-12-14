package com.vasnatech.commons.katip.template.part;

import com.vasnatech.commons.katip.template.Node;
import com.vasnatech.commons.katip.template.renderer.RenderContext;

import java.io.IOException;

public interface Part extends Node {

    void render(Appendable appendable, RenderContext renderContext) throws IOException;
}
