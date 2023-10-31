package com.vasnatech.commons.expression.parser;

import com.vasnatech.commons.expression.Expression;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultExpressionParserTest {

    static DefaultExpressionParser parser;

    @BeforeAll
    static void beforeAll() {
        parser = new DefaultExpressionParser();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "true",
            "false",
            "1",
            "1.2",
            "'abc'",
            "'a(b,c(d,e).f.g().h,i.j(k(l,m)))'",
            "a",
            "a.b",
            "a.b()",
            "a.b.c",
            "a.b().c",
            "a()",
            "a().b",
            "a().b()",
            "a(true,1.2,'abc')",
            "a.b(true,1.2,'abc')",
            "a.b('abc',1.2,false)",
            "a.b(1.2,'abc',true)",
            "a(true,1.2,'abc').b",
            "a(b())",
            "a(b(),c(d,e),f)",
            "a.b(c(),d(e,f),g).h().i",
            "a('b')",
            "a('b','c')",
            "a('b','c','d')",
            "a(b)",
            "a(b,c)",
            "a(b,c,d)",
            "a(b,c(d,e),f)",
            "a(b,c(d,e).f.g().h,i.j(k(l,m)))",
            "a(b(c(d(e()))))",
            "a(b(c(d(e))))",
            "a(b(c(d(e,f))))",
            "a(b(c(d(e,f(g())))))",
            "a(b,c())",
            "a(b(),c)",
            "a(b(),c())",
            "a(b().c,d)"
    })
    void parse(String expected) {
        Expression expression = parser.parse(expected);
        String actual = expression == null ? null : expression.toString();
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }
}
