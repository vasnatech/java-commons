package com.vasnatech.commons.collection;

import java.util.Iterator;

public interface CachedIterator<E> extends Iterator<E> {

    /**
     * Infinite cache size for negative values.
     * @return the cache size
     */
    int cacheSize();

    /**
     * @see Iterator#hasNext()
     */
    @Override
    boolean hasNext();

    /**
     * @see Iterator#next()
     */
    @Override
    E next();

    /**
     * @return if iterator has previous item
     */
    boolean hasPrevious();

    /**
     * @return the previous
     */
    E previous();

    static <E> CachedIterator<E> of(Iterator<E> source) {
        return new CachedIteratorImpl<>(source);
    }

    static <E> CachedIterator<E> of(Iterator<E> source, int cacheSize) {
        return new CachedIteratorImpl<>(source, cacheSize);
    }
}
