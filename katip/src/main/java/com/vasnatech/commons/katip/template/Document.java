package com.vasnatech.commons.katip.template;

import com.vasnatech.commons.katip.template.part.Tag;

public class Document {

    final Tag root;

    public Document(Tag root) {
        this.root = root;
    }

    public Tag root() {
        return root;
    }
}
