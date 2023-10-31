package com.vasnatech.commons.expression.parser;

import com.vasnatech.commons.collection.CachedIterator;
import com.vasnatech.commons.expression.*;
import com.vasnatech.commons.text.token.Token;
import com.vasnatech.commons.text.token.Tokenizer;

import java.util.Iterator;

public class DefaultExpressionParser implements ExpressionParser {

    static final int START = 0;
    static final int CONSTANT = 10;
    static final int NUMBER = 11;
    static final int FLOAT = 12;
    static final int BOOLEAN = 13;
    static final int STRING = 15;
    static final int VARIABLE = 20;
    static final int FUNCTION = 21;
    static final int MEMBER = 30;
    static final int PARAM_START = 40;
    static final int PARAM_END = 41;
    static final int PARAM_NEXT = 42;
    static final int END = 9;

    enum ExpressionTokenType {WhiteSpace, OpenParenthesis, CloseParenthesis, Dot, Comma, Quote}

    final Tokenizer<ExpressionTokenType> tokenizer;

    public DefaultExpressionParser() {
        tokenizer = new Tokenizer<>(
                new Token<>("(", ExpressionTokenType.OpenParenthesis),
                new Token<>(")", ExpressionTokenType.CloseParenthesis),
                new Token<>(".", ExpressionTokenType.Dot),
                new Token<>(",", ExpressionTokenType.Comma),
                new Token<>("'", ExpressionTokenType.Quote),
                new Token<>(" ", ExpressionTokenType.WhiteSpace, false),
                new Token<>("\t", ExpressionTokenType.WhiteSpace, false),
                new Token<>("\b", ExpressionTokenType.WhiteSpace, false)
        );
    }

    @Override
    public Expression parse(CharSequence source) throws ExpressionException {
        Context ctx = new Context(source, tokenizer.tokenize(source));
        return parse(ctx);
    }

    Expression parse(Context ctx) throws ExpressionException {
        Expression currentExpression = null;
        while (ctx.hasNext() || ctx.state == END) {
            if (ctx.state != END) {
                ctx.next();
            }
            switch (ctx.state) {
                case START -> {
                    ctx.previous();
                    ctx.state = CONSTANT;
                }
                case CONSTANT -> {
                    if (ctx.currentToken.getValue() == ExpressionTokenType.Quote) {
                        currentExpression = new ConstantExpression("");
                        ctx.state = STRING;
                    } else {
                        if (ctx.currentToken.getValue() != null) {
                            throw new ExpressionException("Expecting identifier, number, boolean or string but found '" + ctx.currentToken.getMatch() + ".");
                        }
                        ctx.previous();
                        ctx.state = NUMBER;
                    }
                }
                case STRING -> {
                    if (ctx.currentToken.getValue() == ExpressionTokenType.Quote) {
                        ctx.state = END;
                    } else {
                        ConstantExpression constantExpression = (ConstantExpression) currentExpression;
                        currentExpression = new ConstantExpression(constantExpression.get(null, null) + ctx.currentToken.getMatch());
                        ctx.state = STRING;
                    }
                }
                case NUMBER -> {
                    Long number = parseLong(ctx.currentToken.getMatch());
                    if (number != null) {
                        currentExpression = new ConstantExpression(number);
                        if (ctx.iterator.hasNext()) {
                            ctx.currentToken = ctx.iterator.next();
                            if (ctx.currentToken.getValue() == ExpressionTokenType.Dot) {
                                ctx.state = FLOAT;
                            } else {
                                ctx.previous();
                                ctx.state = END;
                            }

                        } else {
                            ctx.state = END;
                        }
                    } else {
                        ctx.previous();
                        ctx.state = BOOLEAN;
                    }
                }
                case FLOAT -> {
                    if (ctx.currentToken.getValue() != null) {
                        ctx.previous();
                        ctx.state = END;
                    }
                    Long number = parseLong(ctx.currentToken.getMatch());
                    if (number == null || number < 0) {
                        throw new ExpressionException("Expecting non negative number but found '" + ctx.currentToken.getMatch() + ".");
                    }
                    ConstantExpression constantExpression = (ConstantExpression) currentExpression;
                    Double decimal = Double.valueOf(constantExpression + "." + number);
                    currentExpression = new ConstantExpression(decimal);
                    ctx.state = END;
                }
                case BOOLEAN -> {
                    Boolean bool = parseBoolean(ctx.currentToken.getMatch());
                    if (bool != null) {
                        currentExpression = new ConstantExpression(bool);
                        ctx.state = END;
                    } else {
                        ctx.previous();
                        ctx.state = VARIABLE;
                    }

                }
                case VARIABLE -> {
                    if (!isJavaIdentifier(ctx.currentToken.getMatch())) {
                        throw new ExpressionException("Expecting identifier but found '" + ctx.currentToken.getMatch() + ".");
                    }
                    if (ctx.hasNext()) {
                        Token<ExpressionTokenType> previousToken = ctx.currentToken;
                        ctx.next();
                        if (ctx.currentToken.getValue() == ExpressionTokenType.Comma || ctx.currentToken.getValue() == ExpressionTokenType.CloseParenthesis) {
                            currentExpression = new ChainedExpression(currentExpression, new PropertyExpression(previousToken.getMatch()));
                            ctx.previous();
                            ctx.state = END;
                        } else if (ctx.currentToken.getValue() == ExpressionTokenType.OpenParenthesis) {
                            ctx.previous();
                            ctx.previous();
                            ctx.state = FUNCTION;
                        } else if (ctx.currentToken.getValue() == ExpressionTokenType.Dot) {
                            currentExpression = new ChainedExpression(currentExpression, new PropertyExpression(previousToken.getMatch()));
                            ctx.previous();
                            ctx.state = MEMBER;
                        } else {
                            throw new ExpressionException("Expecting '.' or '(' but found '" + ctx.currentToken.getMatch() + ".");
                        }
                    } else {
                        currentExpression = new ChainedExpression(currentExpression, new PropertyExpression(ctx.currentToken.getMatch()));
                        ctx.state = END;
                    }
                }
                case FUNCTION -> {
                    FunctionExpression functionExpression = new FunctionExpression(ctx.currentToken.getMatch());
                    currentExpression = new ChainedExpression(currentExpression, functionExpression);
                    ctx.state = PARAM_START;
                    parseFunctionParameters(ctx, functionExpression);
                    if (ctx.hasNext()) {
                        ctx.next();
                        if (ctx.currentToken.getValue() == ExpressionTokenType.Comma || ctx.currentToken.getValue() == ExpressionTokenType.CloseParenthesis) {
                            ctx.previous();
                            ctx.state = END;
                        } else if (ctx.currentToken.getValue() == ExpressionTokenType.Dot) {
                            ctx.previous();
                            ctx.state = MEMBER;
                        }
                    } else {
                        ctx.state = END;
                    }
                }
                case MEMBER -> {
                    if (ctx.currentToken.getValue() != ExpressionTokenType.Dot) {
                        throw new ExpressionException("Expecting '.' but found '" + ctx.currentToken.getMatch() + "'.");
                    }
                    ctx.next();
                    if (!isJavaIdentifier(ctx.currentToken.getMatch())) {
                        throw new ExpressionException("Expecting identifier but found '" + ctx.currentToken.getMatch() + ".");
                    }
                    if (ctx.iterator.hasNext()) {
                        Token<ExpressionTokenType> previousToken = ctx.currentToken;
                        ctx.next();
                        if (ctx.currentToken.getValue() == ExpressionTokenType.OpenParenthesis) {
                            ctx.previous();
                            ctx.previous();
                            ctx.state = FUNCTION;
                        } else {
                            currentExpression = new ChainedExpression(currentExpression, new PropertyExpression(previousToken.getMatch()));
                            ctx.previous();
                            if (ctx.currentToken.getValue() == ExpressionTokenType.Dot) {
                                ctx.state = MEMBER;
                            } else {
                                ctx.state = END;
                            }
                        }
                    } else {
                        currentExpression = new ChainedExpression(currentExpression, new PropertyExpression(ctx.currentToken.getMatch()));
                        ctx.state = END;
                    }
                }
                case END -> {
                    return currentExpression;
                }
            }
        }
        throw new ExpressionException("Unable to parse '" + ctx.expressionAsText + "'.");
    }

    void parseFunctionParameters(Context ctx, FunctionExpression functionExpression) {
        while (ctx.iterator.hasNext()) {
            ctx.next();
            switch (ctx.state) {
                case PARAM_START -> {
                    if (ctx.currentToken.getValue() != ExpressionTokenType.OpenParenthesis) {
                        throw new ExpressionException("Expecting '(' but found '" + ctx.currentToken.getMatch() + "'.");
                    }
                    ctx.state = PARAM_NEXT;
                }
                case PARAM_NEXT -> {
                    if (ctx.currentToken.getValue() == ExpressionTokenType.CloseParenthesis) {
                        ctx.previous();
                    } else {
                        ctx.state = START;
                        ctx.previous();
                        Expression expression = parse(ctx);
                        functionExpression.addParameter(expression);
                    }
                    ctx.state = PARAM_END;
                }
                case PARAM_END -> {
                    if (ctx.currentToken.getValue() == ExpressionTokenType.CloseParenthesis) {
                        return;
                    }  else if (ctx.currentToken.getValue() == ExpressionTokenType.Comma) {
                        ctx.state = PARAM_NEXT;
                    }  else {
                        throw new ExpressionException("Expecting ',' or ')' but found '" + ctx.currentToken.getMatch() + "'.");
                    }
                }
            }
        }
    }

    static boolean isJavaIdentifier(CharSequence identifier) {
        int length = identifier.length();
        if (length == 0) {
            return false;
        }
        if (!Character.isJavaIdentifierStart(identifier.charAt(0))) {
            return false;
        }
        for (int index = 1; index < length; ++index) {
            if (!Character.isJavaIdentifierPart(identifier.charAt(index))) {
                return false;
            }
        }
        return true;
    }

    static Long parseLong(CharSequence text) {
        try {
            return Long.parseLong(text, 0, text.length(), 10);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static Boolean parseBoolean(String text) {
        if ("false".equals(text)) {
            return Boolean.FALSE;
        }
        if ("true".equals(text)) {
            return Boolean.TRUE;
        }
        return null;
    }

    static class Context {
        private final CharSequence expressionAsText;
        private final CachedIterator<Token<ExpressionTokenType>> iterator;

        int state = 0;
        Token<ExpressionTokenType> currentToken = null;

        Context(CharSequence expressionAsText, Iterator<Token<ExpressionTokenType>> iterator) {
            this.expressionAsText = expressionAsText;
            this.iterator = CachedIterator.of(iterator);
        }

        boolean hasNext() {
            return iterator.hasNext();
        }

        void next() {
            currentToken = iterator.next();
        }

        boolean hasPrevious() {
            return iterator.hasPrevious();
        }

        void previous() {
            currentToken = iterator.previous();
        }
    }
}
