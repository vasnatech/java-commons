package com.vasnatech.commons.schema.load;

import com.vasnatech.commons.schema.schema.Schema;
import com.vasnatech.commons.serialize.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public interface SchemaLoader {

    MediaType mediaType();

    <S extends Schema> S load(InputStream in) throws IOException;

    <S extends Schema> S load(Reader reader) throws IOException;
}
