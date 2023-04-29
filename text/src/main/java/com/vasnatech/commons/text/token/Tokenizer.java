package com.vasnatech.commons.text.token;

import com.vasnatech.commons.text.ReaderCharSequence;
import org.eclipse.collections.api.map.primitive.MutableCharObjectMap;
import org.eclipse.collections.impl.map.mutable.primitive.CharObjectHashMap;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Tokenizer<T> {

    final Tree<T> tokenTree;

    @SafeVarargs
    public Tokenizer(Token<T>... tokens) {
        this.tokenTree = new Tree<>();
        Stream.of(tokens).forEach(tokenTree::add);
    }

    public Tokenizer(Collection<Token<T>> tokens) {
        this(new Tree<>());
        tokens.forEach(tokenTree::add);
    }

    Tokenizer(Tree<T> tokenTree) {
        this.tokenTree = tokenTree;
    }

    public Iterator<Token<T>> tokenize(CharSequence template) {
        return new TokenIterator<>(tokenTree, template);
    }

    public Iterator<Token<T>> tokenize(Reader reader) {
        return tokenize(new ReaderCharSequence(reader, 4096));
    }

    public Iterator<Token<T>> tokenize(InputStream in) {
        return tokenize(new ReaderCharSequence(in, 4096));
    }


    static class Tree<T> {
        final Node<T> root;
        int depth;

        public Tree() {
            this.root = new Node<>('\0');
            this.depth = 0;
        }

        void add(Token<T> token) {
            depth = Math.max(depth, token.length);
            root.add(token, 0);
        }

        Token<T> find(CharSequence template, int length, int index) {
            return root.find(template, length, index);
        }
    }

    static class Node<T> {
        final char ch;
        final MutableCharObjectMap<Node<T>> children;

        Token<T> token;

        public Node(char ch) {
            this.ch = ch;
            this.children = new CharObjectHashMap<>(2);
        }

        void add(Token<T> token, int index) {
            if (index >= token.length) {
                this.token = token;
                return;
            }
            char chChild = token.match.charAt(index);
            Node<T> child = children.getIfAbsentPut(chChild, () -> new Node<>(chChild));
            child.add(token, index + 1);
        }

        Token<T> find(CharSequence template, int length, int index) {
            if (index >= length)
                return this.token;
            Node<T> charNode = children.get(template.charAt(index));
            if (charNode == null)
                return this.token;
            return charNode.find(template, length, index + 1);
        }
    }

    static class TokenIterator<T> implements Iterator<Token<T>> {
        final Tree<T> tokenTree;
        final CharSequence template;

        int lastReturnedIndex;
        int matchStartIndex;
        int currentIndex;

        Token<T> matchedToken;

        public TokenIterator(Tree<T> tokenTree, CharSequence template) {
            this.tokenTree = tokenTree;
            this.template = template;

            this.lastReturnedIndex = 0;
            this.matchStartIndex = 0;
            this.currentIndex = 0;
            this.matchedToken = null;
        }

        @Override
        public boolean hasNext() {
            return lastReturnedIndex < template.length();
        }

        @Override
        public Token<T> next() {
            if (lastReturnedIndex < matchStartIndex) {
                CharSequence match = template.subSequence(lastReturnedIndex, matchStartIndex);
                lastReturnedIndex = matchStartIndex;
                return new Token<>(match.toString(), null);
            }
            if (matchStartIndex < currentIndex) {
                lastReturnedIndex = matchStartIndex = currentIndex;
                Token<T> token = matchedToken;
                matchedToken = null;
                return token;
            }
            if (matchStartIndex >= template.length()) {
                throw new NoSuchElementException();
            }

            // Here currentIndex and matchStartIndex and lastReturnedIndex are same
            // also matchedToken is null

            while (matchStartIndex < template.length()) {
                Token<T> token = tokenTree.find(template, template.length(), matchStartIndex);
                if (token == null) {
                    ++matchStartIndex;
                    ++currentIndex;
                    continue;
                }
                if (token.isIterable()) {
                    currentIndex = matchStartIndex + token.length;
                    matchedToken = token;
                    break;
                }
                else {
                    matchStartIndex += token.length;
                    currentIndex = matchStartIndex;
                    lastReturnedIndex = matchStartIndex;
                }
            }

            return next();
        }
    }
}
