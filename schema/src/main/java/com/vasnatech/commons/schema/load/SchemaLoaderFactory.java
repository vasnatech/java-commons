package com.vasnatech.commons.schema.load;

import java.util.Map;

public abstract class SchemaLoaderFactory {

    public abstract SchemaLoader create(Map<String, ?> config);
}
