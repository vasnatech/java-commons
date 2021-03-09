package com.vasnatech.commons.collection;

import java.util.Iterator;

public final class Iterables {

    private Iterables() {}

    public static <T> Iterable<T> from(Iterator<T> iterator) {
        return () -> iterator;
    }

    public static <T> T first(Iterable<T> iterable) {
        return Iterators.first(iterable.iterator());
    }

    public static <T> T last(Iterable<T> iterable) {
        return Iterators.last(iterable.iterator());
    }

    public static <T> T get(Iterable<T> iterable, int index) {
        return Iterators.get(iterable.iterator(), index);
    }
}