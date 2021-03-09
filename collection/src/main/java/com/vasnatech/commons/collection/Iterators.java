package com.vasnatech.commons.collection;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public final class Iterators {

    private Iterators() {}

    public static <T, R> Iterator<R> from(Iterator<T> iterator, Function<T, R> mapper) {
        return new Iterator<>() {
            public boolean hasNext() { return iterator.hasNext(); }
            public R next() { return mapper.apply(iterator.next()); }
        };
    }

    public static <T, R> ListIterator<R> from(ListIterator<T> iterator, Function<T, R> mapper) {
        return new ListIterator<>() {
            public boolean hasNext() { return iterator.hasNext(); }
            public R next() { return mapper.apply(iterator.next()); }
            public boolean hasPrevious() { return iterator.hasPrevious(); }
            public R previous() { return mapper.apply(iterator.previous()); }
            public int nextIndex() { return iterator.nextIndex(); }
            public int previousIndex() { return iterator.previousIndex(); }
            public void remove() { iterator.remove(); }
            public void set(R r) { throw new UnsupportedOperationException(); }
            public void add(R r) { throw new UnsupportedOperationException(); }
        };
    }

    public static <T, R> ListIterator<R> from(ListIterator<T> iterator, Function<T, R> mapper, Function<R, T> inverseMapper) {
        return new ListIterator<>() {
            public boolean hasNext() { return iterator.hasNext(); }
            public R next() { return mapper.apply(iterator.next()); }
            public boolean hasPrevious() { return iterator.hasPrevious(); }
            public R previous() { return mapper.apply(iterator.previous()); }
            public int nextIndex() { return iterator.nextIndex(); }
            public int previousIndex() { return iterator.previousIndex(); }
            public void remove() { iterator.remove(); }
            public void set(R r) { iterator.set(inverseMapper.apply(r)); }
            public void add(R r) { iterator.add(inverseMapper.apply(r)); }
        };
    }

    public static <T> T get(Iterator<T> iterator, int index) {
        if (index < 0) throw new NoSuchElementException();
        for (int i=0; i<index; ++i)
            iterator.next();
        return iterator.next();
    }

    public static <T> T first(Iterator<T> iterator) {
        return iterator.next();
    }

    public static <T> T last(Iterator<T> iterator) {
        T last = null;
        while(iterator.hasNext())
            last = iterator.next();
        if (last == null)
            throw  new NoSuchElementException();
        return last;
    }

    public static <T> T last(ListIterator<T> iterator) {
        return iterator.previous();
    }
}
