package com.vasnatech.commons.schema.load;

import com.vasnatech.commons.schema.schema.Schema;
import com.vasnatech.commons.serialize.MediaType;

import java.io.IOException;
import java.io.InputStream;

public interface SchemaLoader {

    MediaType mediaType();

    <S extends Schema> S load(InputStream in) throws IOException;
}
