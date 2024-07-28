package com.vasnatech.commons.collection;

import com.vasnatech.commons.type.tuple.Single;

import java.util.Iterator;
import java.util.function.Consumer;

public interface SingleIterator<FIRST> extends Iterator<Single<FIRST>> {

    @Override
    boolean hasNext();

    boolean firstHasNext();

    FIRST firstNext();

    default void forEachRemainingFirst(Consumer<? super FIRST> action) {
        while (firstHasNext())
            action.accept(firstNext());
    }

    @Override
    default void forEachRemaining(Consumer<? super Single<FIRST>> action) {
        while (hasNext())
            action.accept(next());
    }


    static <FIRST> SingleIterator<FIRST> of(Iterable<FIRST> firstIterable) {
        return of(firstIterable.iterator());
    }

    static <FIRST> SingleIterator<FIRST> of(Iterator<FIRST> firstIterator) {
        return new Default<>(firstIterator);
    }



    class Default<FIRST> implements SingleIterator<FIRST> {

        final Iterator<FIRST> firstIterator;

        Default(Iterator<FIRST> firstIterator) {
            this.firstIterator = firstIterator;
        }

        @Override
        public boolean hasNext() {
            return firstIterator.hasNext();
        }

        @Override
        public boolean firstHasNext() {
            return firstIterator.hasNext();
        }

        @Override
        public FIRST firstNext() {
            return firstIterator.next();
        }

        @Override
        public Single<FIRST> next() {
            return Single.of(firstIterator.next());
        }
    }
}
