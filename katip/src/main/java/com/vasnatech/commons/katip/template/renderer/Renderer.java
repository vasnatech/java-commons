package com.vasnatech.commons.katip.template.renderer;

import com.vasnatech.commons.katip.template.Document;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public interface Renderer {

    default void render(Document document, OutputStream out, Map<String, Object> parameters) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            render(document, writer, parameters);
        }
    }

    void render(Document document, Appendable out, Map<String, Object> parameters) throws IOException;
}
