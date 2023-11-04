package com.vasnatech.commons.schema.load;

import com.vasnatech.commons.schema.SupportedMediaTypes;
import com.vasnatech.commons.serialize.MediaType;

import java.util.HashMap;
import java.util.Map;

public final class SchemaLoaderFactories {

    private SchemaLoaderFactories() {
    }

    static final Map<MediaType, SchemaLoaderFactory> FACTORIES = new HashMap<>();
    static {
        FACTORIES.put(SupportedMediaTypes.JSON, new JacksonJsonSchemaLoaderFactory());
    }

    public static SchemaLoaderFactory get(MediaType mediaType) {
        return FACTORIES.get(mediaType);
    }
}
