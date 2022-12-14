package com.vasnatech.commons.katip.template.parser;

import com.vasnatech.commons.katip.template.Document;
import com.vasnatech.commons.resource.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DefaultParserTest {

    @Test
    void parse() throws IOException {
        DefaultParser parser = new DefaultParser();
        Document document = parser.parse(Resources.asString(DefaultParserTest.class, "../mysql-template"));
        System.out.println(document);
    }
}
