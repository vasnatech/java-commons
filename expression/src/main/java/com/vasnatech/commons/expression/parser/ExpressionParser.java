package com.vasnatech.commons.expression.parser;

import com.vasnatech.commons.expression.Expression;
import com.vasnatech.commons.expression.ExpressionException;
import com.vasnatech.commons.text.ReaderCharSequence;

import java.io.InputStream;
import java.io.Reader;

public interface ExpressionParser {

    default Expression parse(InputStream in) throws ExpressionException {
        return parse(new ReaderCharSequence(in, 4096));
    }

    default Expression parse(Reader reader) throws ExpressionException {
        return parse(new ReaderCharSequence(reader, 4096));
    }

    Expression parse(CharSequence source) throws ExpressionException;
}
