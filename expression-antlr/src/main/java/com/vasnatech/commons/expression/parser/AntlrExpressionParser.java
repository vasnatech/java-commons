package com.vasnatech.commons.expression.parser;

import com.vasnatech.commons.expression.ConstantExpression;
import com.vasnatech.commons.expression.Expression;
import com.vasnatech.commons.expression.ExpressionException;
import com.vasnatech.commons.expression.FunctionExpression;
import com.vasnatech.commons.expression.antlr.ExpressionLexer;
import com.vasnatech.commons.expression.antlr.ExpressionParserListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import static com.vasnatech.commons.expression.antlr.ExpressionParser.*;

public class AntlrExpressionParser implements ExpressionParser {

    @Override
    public Expression parse(Reader reader) throws ExpressionException {
        try {
            return parse(CharStreams.fromReader(reader));
        } catch (IOException e) {
            throw new ExpressionException(e.getMessage(), e);
        }
    }

    @Override
    public Expression parse(InputStream in) throws ExpressionException {
        try {
            return parse(CharStreams.fromStream(in));
        } catch (IOException e) {
            throw new ExpressionException(e.getMessage(), e);
        }
    }

    @Override
    public Expression parse(CharSequence source) throws ExpressionException {
        return parse(CharStreams.fromString(source.toString()));
    }

    Expression parse(CharStream input) throws ExpressionException {
        ExpressionLexer lexer = new ExpressionLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        com.vasnatech.commons.expression.antlr.ExpressionParser parser = new com.vasnatech.commons.expression.antlr.ExpressionParser(tokens);
        parser.addParseListener(new Listener());
        ExpressionContext expression = parser.expression();
        System.out.println(expression.toStringTree(parser));
        return null;
    }

    public static void main(String[] args) {
        ExpressionParser parser = new AntlrExpressionParser();

        Expression expression = parser.parse("field.getDDL().getInverseTable().getColumns().values().get(12.3)");
        //parser.parse("a.b().c = 12");
    }

    static class Listener implements ExpressionParserListener {

        Expression currentExpression;

        @Override
        public void enterExpression(ExpressionContext ctx) {

        }

        @Override
        public void exitExpression(ExpressionContext ctx) {

        }

        @Override
        public void enterConstant(ConstantContext ctx) {
            int type = ctx.getStart().getType();
            if (type == DECIMAL_LITERAL) {
                currentExpression = new ConstantExpression(Integer.valueOf(ctx.DECIMAL_LITERAL().getText()));
            } else if (type == FLOAT_LITERAL) {
                currentExpression = new ConstantExpression(Double.valueOf(ctx.FLOAT_LITERAL().getText()));
            } else if (type == BOOLEAN_LITERAL) {
                currentExpression = new ConstantExpression(Boolean.valueOf(ctx.BOOLEAN_LITERAL().getText()));
            } else if (type == STRING_LITERAL) {
                currentExpression = new ConstantExpression(ctx.STRING_LITERAL().getText());
            }
        }

        @Override
        public void exitConstant(ConstantContext ctx) {
        }

        @Override
        public void enterChainable(ChainableContext ctx) {

        }

        @Override
        public void exitChainable(ChainableContext ctx) {

        }

        @Override
        public void enterArguments(ArgumentsContext ctx) {
            if (currentExpression instanceof FunctionExpression) {
            }
        }

        @Override
        public void exitArguments(ArgumentsContext ctx) {

        }

        @Override
        public void visitTerminal(TerminalNode node) {

        }

        @Override
        public void visitErrorNode(ErrorNode node) {

        }

        @Override
        public void enterEveryRule(ParserRuleContext ctx) {

        }

        @Override
        public void exitEveryRule(ParserRuleContext ctx) {

        }
    }
}
