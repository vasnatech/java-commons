package com.vasnatech.commons.schema.validate;

import com.vasnatech.commons.schema.schema.Schema;

public interface SchemaValidatorFactory<S extends Schema, SV extends SchemaValidator<S>> {

    SV create(String version);
}
