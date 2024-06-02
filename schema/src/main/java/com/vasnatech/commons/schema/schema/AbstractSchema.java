package com.vasnatech.commons.schema.schema;

import java.util.LinkedHashMap;

public abstract class AbstractSchema implements Schema, Node {

    protected final String name;
    protected final LinkedHashMap<String, String> meta;
    protected final String type;

    public AbstractSchema(String name, String type, LinkedHashMap<String, String> meta) {
        this.name = name;
        this.type = type;
        this.meta = meta;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String type() {
        return type;
    }

    public LinkedHashMap<String, String> meta() {
        return meta;
    }
}
