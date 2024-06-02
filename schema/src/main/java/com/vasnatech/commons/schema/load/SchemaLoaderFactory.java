package com.vasnatech.commons.schema.load;

import java.util.Map;

public interface SchemaLoaderFactory {

    SchemaLoader create(Map<String, ?> config);
}
