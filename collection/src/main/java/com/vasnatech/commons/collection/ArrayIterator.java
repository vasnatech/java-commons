package com.vasnatech.commons.collection;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayIterator<E> implements ListIterator<E> {

    final E[] elements;
    int currentIndex;
    boolean iterationStarted;

    @SafeVarargs
    public ArrayIterator(E... elements) {
        this.elements = elements;
        this.currentIndex = -1;
        this.iterationStarted = false;
    }

    @Override
    public boolean hasNext() {
        return (currentIndex + 1) < elements.length;
    }

    @Override
    public E next() {
        iterationStarted = true;
        return elements[++currentIndex];
    }

    @Override
    public boolean hasPrevious() {
        return iterationStarted ? currentIndex >= 0 : elements.length > 0;
    }

    @Override
    public E previous() {
        currentIndex = iterationStarted ? currentIndex : elements.length - 1;
        iterationStarted = true;
        return elements[currentIndex--];
    }

    @Override
    public int nextIndex() {
        return Math.min(currentIndex + 1, elements.length);
    }

    @Override
    public int previousIndex() {
        return iterationStarted ? Math.max(currentIndex, -1) : elements.length - 1;
    }

    @Override
    public void remove() {
        throw new NoSuchElementException();
    }

    @Override
    public void set(E e) {
        elements[currentIndex] = e;
    }

    @Override
    public void add(E e) {
        throw new NoSuchElementException();
    }

    @SafeVarargs
    public static <E> ArrayIterator<E> of(E... elements) {
        return new ArrayIterator<>(elements);
    }
}
