package com.vasnatech.commons.text.token;

import java.util.Objects;

public class Token<T> {

    final int length;
    final String match;
    final T value;
    final boolean iterable;

    public Token(String match, T value) {
        this(match, value, true);
    }

    public Token(String match, T value, boolean iterable) {
        this.length = match.length();
        this.match = match;
        this.value = value;
        this.iterable = iterable;
    }

    public String getMatch() {
        return match;
    }

    public T getValue() {
        return value;
    }

    public boolean isIterable() {
        return iterable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token<?> token = (Token<?>) o;
        if (!Objects.equals(match, token.match)) return false;
        return Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        int result = match != null ? match.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Token{match=" + match + ", value=" + value + '}';
    }
}
