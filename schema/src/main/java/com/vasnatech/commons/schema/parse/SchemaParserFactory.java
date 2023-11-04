package com.vasnatech.commons.schema.parse;

import com.vasnatech.commons.schema.schema.Schema;

public interface SchemaParserFactory<S extends Schema, SP extends SchemaParser<S>> {

    SP create(String version);
}
