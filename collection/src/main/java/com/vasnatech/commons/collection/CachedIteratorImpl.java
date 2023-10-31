package com.vasnatech.commons.collection;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class CachedIteratorImpl<E> implements CachedIterator<E> {

    final Iterator<E> source;
    final int cacheSize;
    final Deque<E> previousItems;
    final Deque<E> reversedItems;

    public CachedIteratorImpl(Iterator<E> source) {
        this(source, Integer.MAX_VALUE);
    }

    public CachedIteratorImpl(Iterator<E> source, int cacheSize) {
        this.source = source;
        this.cacheSize = Math.max(cacheSize, 0);
        this.previousItems = new LinkedList<>();
        this.reversedItems = new LinkedList<>();
    }

    @Override
    public int cacheSize() {
        return cacheSize;
    }

    @Override
    public boolean hasNext() {
        return !reversedItems.isEmpty() || source.hasNext();
    }

    @Override
    public E next() {
        E next = reversedItems.isEmpty() ? source.next() : reversedItems.pop();
        previousItems.push(next);
        if (previousItems.size() > cacheSize) {
            previousItems.removeLast();
        }
        return next;
    }

    @Override
    public boolean hasPrevious() {
        return !previousItems.isEmpty();
    }

    @Override
    public E previous() {
        if (previousItems.isEmpty()) {
            throw new NoSuchElementException();
        }
        E previous = previousItems.pop();
        reversedItems.push(previous);
        return previous;
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        reversedItems.forEach(action);
        source.forEachRemaining(action);
    }
}
