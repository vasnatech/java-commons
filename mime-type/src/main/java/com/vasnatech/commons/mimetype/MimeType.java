package com.vasnatech.commons.mimetype;

import java.util.Set;

public class MimeType {

    private final String name;
    private final Set<String> fileTypes;

    public MimeType(String name, Set<String> fileTypes) {
        this.name = name;
        this.fileTypes = fileTypes;
    }

    public String name() {
        return name;
    }

    public Set<String> fileTypes() {
        return fileTypes;
    }
}
