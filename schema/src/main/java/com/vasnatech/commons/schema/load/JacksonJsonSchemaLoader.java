package com.vasnatech.commons.schema.load;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.vasnatech.commons.serialize.MediaType;
import com.vasnatech.commons.schema.Modules;
import com.vasnatech.commons.schema.SupportedMediaTypes;
import com.vasnatech.commons.schema.parse.ParserException;
import com.vasnatech.commons.schema.parse.SchemaParser;
import com.vasnatech.commons.schema.parse.SchemaParserFactory;
import com.vasnatech.commons.schema.schema.Schema;
import com.vasnatech.commons.schema.validate.SchemaValidator;
import com.vasnatech.commons.schema.validate.SchemaValidatorFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

public class JacksonJsonSchemaLoader implements SchemaLoader {

    final JsonFactory jsonFactory;
    final Map<String, ?> config;

    public JacksonJsonSchemaLoader(Map<String, ?> config) {
        this(new JsonFactory(), config);
    }

    public JacksonJsonSchemaLoader(JsonFactory jsonFactory, Map<String, ?> config) {
        this.jsonFactory = jsonFactory;
        this.config = config;
    }

    @Override
    public MediaType mediaType() {
        return SupportedMediaTypes.JSON;
    }

    boolean isNormalizeEnabled() {
        return Boolean.parseBoolean(String.valueOf(config.get("normalize")));
    }

    boolean isValidateEnabled() {
        return Boolean.parseBoolean(String.valueOf(config.get("validate")));
    }

    @Override
    public <S extends Schema> S load(InputStream in) throws IOException {
        return load(jsonFactory.createParser(in));
    }

    @Override
    public <S extends Schema> S load(Reader reader) throws IOException {
        return load(jsonFactory.createParser(reader));
    }

    public <S extends Schema> S load(JsonParser parser) throws IOException {
        Map<String, String> meta = null;
        parser.nextToken();
        if (parser.currentToken() == JsonToken.START_OBJECT) {
            parser.nextToken();
            if (parser.currentToken() == JsonToken.FIELD_NAME) {
                String fieldName = parser.currentName();
                if ("meta".equals(fieldName)) {
                    meta = parseMeta(parser);
                }
            }
        }
        if (meta == null) {
            throw new ParserException("Unable to find meta block.");
        }
        parser.nextToken();

        String schemaType = meta.get("@schema-type");
        String schemaVersion = meta.get("@schema-version");
        SchemaParserFactory<S, SchemaParser<S>> factory = Modules.getSchemaParserFactoryByMediaType(schemaType, mediaType());
        SchemaParser<S> schemaParser = factory.create(schemaVersion);
        S schema = schemaParser.continueParsing(parser, meta);
        if (isNormalizeEnabled()) {
            schema = schemaParser.normalize(schema);
        }
        if (isValidateEnabled()) {
            SchemaValidatorFactory<S, SchemaValidator<S>> validatorFactory = Modules.getSchemaValidatorFactory(schemaType);
            SchemaValidator<S> validator = validatorFactory.create(schemaVersion);
            validator.validateAndThrow(schema);
        }
        return schema;
    }

    private Map<String, String> parseMeta(JsonParser parser) throws IOException {
        Map<String, String> meta = new LinkedHashMap<>();
        parser.nextToken();
        if (parser.currentToken() == JsonToken.START_OBJECT) {
            parser.nextToken();
            while (parser.currentToken() == JsonToken.FIELD_NAME) {
                meta.put(parser.currentName(), parser.nextTextValue());
                parser.nextToken();
            }
        }
        return meta;
    }
}
