package com.vasnatech.commons.collection;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Streams {

    private Streams() {}

    public static <T> Stream<T> from(Iterator<T> iterator) {
        return from(iterator, false);
    }

    public static <T> Stream<T> from(Iterator<T> iterator, boolean parallel) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        iterator,
                        Spliterator.ORDERED
                ),
                parallel
        );
    }

    public static <T> Stream<T> from(Iterable<T> iterable) {
        return from(iterable, false);
    }

    public static <T> Stream<T> from(Iterable<T> iterable, boolean parallel) {
        return from(iterable.iterator(), parallel);
    }
}