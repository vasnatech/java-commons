package com.vasnatech.commons.katip.template.parser;

import com.vasnatech.commons.katip.template.Document;
import com.vasnatech.commons.katip.template.expression.ConstantExpression;
import com.vasnatech.commons.katip.template.expression.Expression;
import com.vasnatech.commons.katip.template.expression.PropertyExpression;
import com.vasnatech.commons.katip.template.part.Tag;
import com.vasnatech.commons.katip.template.part.TagRenderer;
import com.vasnatech.commons.katip.template.part.TagRenderers;
import com.vasnatech.commons.katip.template.part.Text;
import com.vasnatech.commons.text.token.Token;
import com.vasnatech.commons.text.token.Tokenizer;
import org.apache.commons.lang3.StringUtils;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DefaultParser implements Parser {

    enum TagTokenType {Open, Close, LineComment, EndOfLine}

    enum TagAttributeTokenType {WhiteSpace, Equal, DoubleQuotes}

    enum ExpressionTokenType {WhiteSpace, OpenParenthesis, CloseParenthesis, Dot, Comma}

    final Tokenizer<TagTokenType> tagTokenizer;
    final Tokenizer<TagAttributeTokenType> tagAttributeTokenizer;
    final Tokenizer<ExpressionTokenType> expressionTokenizer;

    DefaultParser() {
        tagTokenizer = new Tokenizer<>(
                new Token<>("<<--", TagTokenType.Open),
                new Token<>("-->>", TagTokenType.Close),
                new Token<>("<<//", TagTokenType.LineComment),
                new Token<>("\r\n", TagTokenType.EndOfLine),
                new Token<>("\n", TagTokenType.EndOfLine)
        );
        tagAttributeTokenizer = new Tokenizer<>(
                new Token<>("=", TagAttributeTokenType.Equal),
                new Token<>("\"", TagAttributeTokenType.DoubleQuotes),
                new Token<>(" ", TagAttributeTokenType.WhiteSpace),
                new Token<>("\t", TagAttributeTokenType.WhiteSpace)
        );
        expressionTokenizer = new Tokenizer<>(
                new Token<>("(", ExpressionTokenType.OpenParenthesis),
                new Token<>(")", ExpressionTokenType.OpenParenthesis),
                new Token<>(".", ExpressionTokenType.Dot),
                new Token<>(",", ExpressionTokenType.Comma),
                new Token<>(" ", ExpressionTokenType.WhiteSpace, false),
                new Token<>("\t", ExpressionTokenType.WhiteSpace, false),
                new Token<>("\b", ExpressionTokenType.WhiteSpace, false)
        );
    }

    @Override
    public Document parse(CharSequence source) {
        final Iterator<Token<TagTokenType>> iterator = tagTokenizer.tokenize(source);
        final Deque<Tag> containers = new LinkedList<>();
        containers.offer(new Tag(TagRenderers.get("root")));
        Token<TagTokenType> previousToken = null;
        Token<TagTokenType> currentToken;
        TagTokenType currentTokenType;
        AtomicInteger line = new AtomicInteger(1);
        while (iterator.hasNext()) {
            currentToken = iterator.next();
            currentTokenType = currentToken.getValue();
            if (currentTokenType == TagTokenType.Open) {
                currentToken = parseTagOpen(previousToken, currentToken, iterator, line, containers);
            } else if (currentTokenType == null) {
                currentToken = parseTagText(previousToken, currentToken, iterator, line, containers);
            } else if (currentTokenType == TagTokenType.EndOfLine) {
                currentToken = parseTagEndOfLine(previousToken, currentToken, iterator, line, containers);
            } else if (currentTokenType == TagTokenType.LineComment) {
                currentToken = parseTagLineComment(previousToken, currentToken, iterator, line, containers);
            } else if (currentTokenType == TagTokenType.Close) {
                throw new RuntimeException("Invalid tag at line " + line + ". Not expecting close token.");
            }
            previousToken = currentToken;
        }

        if (containers.size() > 1) {
            throw new RuntimeException("Not closed tag " + containers.peek().renderer().name() + ".");
        }
        if (containers.size() < 1) {
            throw new RuntimeException("Too many end tags.");
        }

        return new Document(containers.poll());
    }

    Token<TagTokenType> parseTagOpen(Token<TagTokenType> previousToken, Token<TagTokenType> currentToken, Iterator<Token<TagTokenType>> iterator, AtomicInteger line, Deque<Tag> containers) {
        if (!iterator.hasNext()) {
            throw new RuntimeException("Invalid tag at line " + line + ". Expecting expression.");
        }
        Token<TagTokenType> expressionToken = iterator.next();
        if (expressionToken.getValue() != null) {
            throw new RuntimeException("Invalid tag at line " + line + ". Expecting expression found " + expressionToken.getMatch());
        }

        if (!iterator.hasNext()) {
            throw new RuntimeException("Invalid tag at line " + line + ". Expecting close token.");
        }
        Token<TagTokenType> closeToken = iterator.next();
        if (closeToken.getValue() != TagTokenType.Close) {
            throw new RuntimeException("Invalid tag at line " + line + ". Expecting close token.");
        }

        String tagText = expressionToken.getMatch().trim();
        if ("end".equals(tagText)) {
            containers.poll();
            if (containers.isEmpty()) {
                throw new RuntimeException("Unexpected end tag at line " + line + ".");
            }
            return closeToken;
        }

        Tag tag = parseTag(tagText, line);
        containers.peek().addChild(tag);

        if (tag.renderer().isContainer()) {
            containers.offer(tag);
        }

        return closeToken;
    }

    Token<TagTokenType> parseTagLineComment(Token<TagTokenType> previousToken, Token<TagTokenType> currentToken, Iterator<Token<TagTokenType>> iterator, AtomicInteger line, Deque<Tag> containers) {
        while (iterator.hasNext()) {
            final Token<TagTokenType> token = iterator.next();
            if (token.getValue() == TagTokenType.EndOfLine) {
                line.incrementAndGet();
                return token;
            }
        }
        return null;
    }

    Token<TagTokenType> parseTagText(Token<TagTokenType> previousToken, Token<TagTokenType> currentToken, Iterator<Token<TagTokenType>> iterator, AtomicInteger line, Deque<Tag> containers) {
        Text text = new Text(currentToken.getMatch());
        containers.peek().addChild(text);
        return currentToken;
    }

    Token<TagTokenType> parseTagEndOfLine(Token<TagTokenType> previousToken, Token<TagTokenType> currentToken, Iterator<Token<TagTokenType>> iterator, AtomicInteger line, Deque<Tag> containers) {
        if (previousToken == null || previousToken.getValue() == null) {
            Text text = new Text(currentToken.getMatch());
            containers.peek().addChild(text);
        }
        line.incrementAndGet();
        return currentToken;
    }

    Tag parseTag(String tagText, AtomicInteger line) {
        int index = tagText.indexOf(' ');
        if (index < 0) {
            throw new RuntimeException("Unexpected tag " + tagText + " at line " + line + ".");
        }
        String tagName;
        String tagAttributesAsText;
        tagName = tagText.substring(0, index);
        tagAttributesAsText = tagText.substring(index + 1);

        TagRenderer renderer = TagRenderers.get(tagName);
        if (renderer == null) {
            throw new RuntimeException("Unknown tag renderer " + tagName + " at line " + line + ".");
        }
        Tag tag = new Tag(renderer);
        parseTagAttributes(tag, tagAttributesAsText, line);
        return tag;
    }

    void parseTagAttributes(Tag tag, String tagAttributesAsText, AtomicInteger line) {
        Iterator<Token<TagAttributeTokenType>> iterator = tagAttributeTokenizer.tokenize(tagAttributesAsText);
        String currentAttrName = null;
        String currentExpression = null;
        int state = 0; // 0: start  1: attribute name  2: equals  3: double quotes  4: expression  5: double quotes
        while (iterator.hasNext()) {
            Token<TagAttributeTokenType> token = iterator.next();
            TagAttributeTokenType tokenType = token.getValue();
            String match = token.getMatch();
            if (tokenType == TagAttributeTokenType.WhiteSpace) {
                continue;
            }
            switch (state) {
                case 0 -> {
                    if (tokenType != null || !isJavaIdentifier(match)) {
                        throw new RuntimeException("Expecting attribute name but found '" + match + "' at line " + line + ".");
                    }
                    currentAttrName = match;
                    state = 1;
                }
                case 1 -> {
                    if (tokenType != TagAttributeTokenType.Equal) {
                        throw new RuntimeException("Expecting '=' but found '" + match + "' at line " + line + ".");
                    }
                    state = 2;
                }
                case 2 -> {
                    if (tokenType != TagAttributeTokenType.DoubleQuotes) {
                        throw new RuntimeException("Expecting '\"' but found '" + match + "' at line " + line + ".");
                    }
                    state = 3;
                }
                case 3 -> {
                    if (tokenType != null) {
                        throw new RuntimeException("Expecting an expression but found '" + match + "' at line " + line + ".");
                    }
                    currentExpression = match;
                    state = 4;
                }
                case 4 -> {
                    if (tokenType != TagAttributeTokenType.DoubleQuotes) {
                        throw new RuntimeException("Expecting '\"' but found '" + match + "' at line " + line + ".");
                    }
                    state = 5;
                }
            }
            if (state == 5) {
                Expression expression = parseExpression(currentExpression, line);
                tag.addAttribute(currentAttrName, expression);
                //TODO implement

                currentAttrName = null;
                currentExpression = null;
                state = 0;
            }
        }
        if (state != 0) {
            throw new RuntimeException("Incomplete attribute at line " + line + ".");
        }
    }

    Expression parseExpression(String expressionAsText, AtomicInteger line) {
        Iterator<Token<ExpressionTokenType>> iterator = expressionTokenizer.tokenize(expressionAsText);
        return parseExpression(iterator, line);
    }

    Expression parseExpression(Iterator<Token<ExpressionTokenType>> iterator, AtomicInteger line) {
        int state = 0; // 0: start  1: identifier
        String currentIdentifier = null;
        Expression currentExpression = null;
        Token<ExpressionTokenType> token;
        ExpressionTokenType tokenType;
        String match;
        boolean goNext = true;
        while (iterator.hasNext()) {
            if (goNext) {
                token = iterator.next();
                tokenType = token.getValue();
                match = token.getMatch();
            }
            goNext = true;
            switch (state) {
                case 0 -> {
                    if (tokenType != null) {
                        throw new RuntimeException("Expecting identifier or number but found '" + match + "' at line " + line + ".");
                    }
                    Long integer = parseLong(match);
                    if (integer != null) {
                        currentExpression = new ConstantExpression(integer);
                        state = 11;
                    } else {
                        if (!isJavaIdentifier(match)) {
                            throw new RuntimeException("Expecting identifier or constant but found '" + match + "' at line " + line + ".");
                        }
                        currentIdentifier = match;
                        state = 21;
                    }
                }
                case 11 -> {
                    if (tokenType == ExpressionTokenType.Dot) {
                        state = 12;
                    } else {
                        goNext = false;
                    }
                }
                case 12 -> {
                    if (tokenType != null) {
                        throw new RuntimeException("Expecting number but found '" + match + "' at line " + line + ".");
                    }
                    Long integer = parseLong(match);
                    if (integer == null || integer < 0) {
                        throw new RuntimeException("Expecting number but found '" + match + "' at line " + line + ".");
                    }
                    Double decimal = Double.valueOf(currentExpression + "." + integer);
                    currentExpression = new ConstantExpression(decimal);
                    state = 99;
                }
                case 21 -> {
                    if (tokenType == ExpressionTokenType.Dot) {
                        currentExpression = new PropertyExpression(currentIdentifier);
                        state = 0;
                    } else if (tokenType == ExpressionTokenType.OpenParenthesis) {
                        state = 3;
                    } else {
                        throw new RuntimeException("Expecting '.' or '(' but found '" + match + "' at line " + line + ".");
                    }
                }
            }
            if (state == 99) {
                return currentExpression;
            }
        }
    }

    Token<ExpressionTokenType> parseExpressionText(AtomicReference<Expression> parentExpression, Token<ExpressionTokenType> currentToken, Iterator<Token<ExpressionTokenType>> iterator, AtomicInteger line) {
        String text = currentToken.getMatch();
        Long number = parseLong(text);
        if (number != null) {
            //TODO implement
            return null;
        }
        if (isJavaIdentifier(text)) {
            //TODO implement
            return null;
        }
        throw new RuntimeException("Unexpected identifier" + text + " at line " + line + ".");
    }

    Token<ExpressionTokenType> parseExpressionDot(AtomicReference<Expression> parentExpression, Token<ExpressionTokenType> currentToken, Iterator<Token<ExpressionTokenType>> iterator, AtomicInteger line) {
        if (!iterator.hasNext()) {
            throw new RuntimeException("Unexpected '.' at line " + line + ".");
        }
        final Token<ExpressionTokenType> token = iterator.next();
        while (iterator.hasNext()) {
            final Token<ExpressionTokenType> token = iterator.next();
            if (token.getValue() == ExpressionTokenType.CloseParenthesis) {
                return token;
            }
        }
        //TODO implement.
        return null;
    }

    Token<ExpressionTokenType> parseExpressionOpenParenthesis(AtomicReference<Expression> parentExpression, Token<ExpressionTokenType> currentToken, Iterator<Token<ExpressionTokenType>> iterator, AtomicInteger line) {
        while (iterator.hasNext()) {
            final Token<ExpressionTokenType> token = iterator.next();
            if (token.getValue() == ExpressionTokenType.CloseParenthesis) {
                return token;
            }
        }
        //TODO implement.
        return null;
    }

    class ExpressionParser {
        final AtomicInteger line;
        final Tag tag;
        Token<ExpressionTokenType> currentToken;
        Expression currentExpression;

        ExpressionParser(AtomicInteger line, Tag tag) {
            this.line = line;
            this.tag = tag;
        }

        void parse(CharSequence tagAttributesAsText) {
            AtomicReference<Expression> parentExpression = new AtomicReference<>(null);
            Iterator<Token<ExpressionTokenType>> iterator = expressionTokenizer.tokenize(tagAttributesAsText);
            Token<ExpressionTokenType> currentToken;
            ExpressionTokenType currentTokenType;
            Expression currentExpression = null;
            while (iterator.hasNext()) {
                currentToken = iterator.next();
                currentTokenType = currentToken.getValue();
                if (currentTokenType == null) {
                    currentToken = parseExpressionText(parentExpression, currentToken, iterator, line);
                } else if (currentTokenType == ExpressionTokenType.OpenParenthesis) {
                    currentToken = parseExpressionOpenParenthesis(parentExpression, currentToken, iterator, line);
                } else if (currentTokenType == ExpressionTokenType.CloseParenthesis) {
                    throw new RuntimeException("Invalid expression at line " + line + ". Not expecting close parenthesis.");
                } else if (currentTokenType == ExpressionTokenType.Dot) {
                    currentToken = parseExpressionDot(parentExpression, currentToken, iterator, line);
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
}
