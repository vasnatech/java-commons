package com.vasnatech.commons.expression.parser;

public class DefaultExpressionParserFactory implements ExpressionParserFactory {

    @Override
    public ExpressionParser create() {
        return new DefaultExpressionParser();
    }
}
