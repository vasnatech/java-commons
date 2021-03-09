package com.vasnatech.commons.text;

import com.vasnatech.commons.text.token.Token;
import com.vasnatech.commons.text.token.Tokenizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ParametrizedString {

    public final List<Part> parts;

    public ParametrizedString(List<Part> parts) {
        this.parts = parts;
    }

    public ParametrizedString(String template, final String prefix, final String suffix) {
        Tokenizer<TokenType> tokenizer = new Tokenizer<>(
                new Token<>(prefix, TokenType.Begin),
                new Token<>(suffix, TokenType.End)
        );

        Iterator<Token<TokenType>> iterator = tokenizer.tokenize(template);
        this.parts = new ArrayList<>();
        while (iterator.hasNext()) {
            Token<TokenType> token = iterator.next();
            if (token.getValue() == null)
                parts.add(new StaticPart(token.getMatch()));
            else if (token.getValue() == TokenType.Begin)
                parseParameter(iterator, token);
            else if (token.getValue() == TokenType.End)
                parts.add(new StaticPart(token.getMatch()));
        }
    }

    void parseParameter(Iterator<Token<TokenType>> iterator, Token<TokenType> beginToken) {
        if (!iterator.hasNext()) {
            parts.add(new StaticPart(beginToken.getMatch()));
            return;
        }
        Token<TokenType> token2 = iterator.next();
        if (token2.getValue() == TokenType.Begin) {
            parts.add(new StaticPart(beginToken.getMatch()));
            parseParameter(iterator, token2);
            return;
        }
        if (token2.getValue() == TokenType.End) {
            parts.add(new StaticPart(beginToken.getMatch()));
            parts.add(new StaticPart(token2.getMatch()));
            return;
        }
        if (!iterator.hasNext()) {
            parts.add(new StaticPart(beginToken.getMatch()));
            parts.add(new StaticPart(token2.getMatch()));
            return;
        }
        Token<TokenType> token3 = iterator.next();
        if (token3.getValue() == TokenType.Begin) {
            parts.add(new StaticPart(beginToken.getMatch()));
            parts.add(new StaticPart(token2.getMatch()));
            parseParameter(iterator, token3);
            return;
        }
        parts.add(new ParameterPart(token2.getMatch()));
    }

    public String toString(Map<String, ?> values) {
        StringBuilder builder = new StringBuilder(parts.size() * 16);
        parts.forEach(part -> builder.append(part.evaluate(values)));
        return builder.toString();
    }
    public String toString() {
        return toString(Map.of());
    }
    public String toString(String k0, Object v0) {
        return toString(Map.of(k0, v0));
    }
    public String toString(String k0, Object v0, String k1, Object v1) {
        return toString(Map.of(k0, v0, k1, v1));
    }
    public String toString(String k0, Object v0, String k1, Object v1, String k2, Object v2) {
        return toString(Map.of(k0, v0, k1, v1, k2, v2));
    }
    public String toString(String k0, Object v0, String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        return toString(Map.of(k0, v0, k1, v1, k2, v2, k3, v3));
    }
    public String toString(String k0, Object v0, String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        return toString(Map.of(k0, v0, k1, v1, k2, v2, k3, v3, k4, v4));
    }

    public static Builder builder() {
        return new Builder();
    }

    enum TokenType { Begin, End }

    interface Part {
        String evaluate(Map<String, ?> parameters);
    }

    static class StaticPart implements Part {
        final String text;

        public StaticPart(String text) {
            this.text = text;
        }

        public String evaluate(Map<String, ?> parameters) {
            return text;
        }
    }

    static class ParameterPart implements Part {
        final String key;

        ParameterPart(String key) {
            this.key = key;
        }

        public String evaluate(Map<String, ?> parameters) {
            Object value = parameters.get(key);
            if (value instanceof Supplier)
                value = ((Supplier<?>)value).get();
            return value == null ? null : value.toString();
        }
    }

    public static class Builder {
        final List<Part> parts;

        Builder() {
            parts = new ArrayList<>();
        }

        public Builder string(String text) {
            parts.add(new StaticPart(text));
            return this;
        }

        public Builder parameter(String key) {
            parts.add(new ParameterPart(key));
            return this;
        }

        public ParametrizedString build() {
            return new ParametrizedString(List.copyOf(parts));
        }
    }
}