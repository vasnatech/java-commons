package com.vasnatech.commons.collection;

import com.vasnatech.commons.type.tuble.Pair;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Collections {

    private Collections() {}

    public static <T> Collection<T> deepMerge(Collection<T> value, Collection<T> newValue) {
        if (value == null)
            return newValue;
        if (newValue == null)
            return value;
        value.addAll(newValue);
        return value;
    }

    public static <T, C extends Collection<T>> Pair<C, C> split(C source, Predicate<T> predicate, Supplier<C> collectionFactory) {
        return split(source, predicate, collectionFactory.get(), collectionFactory.get());
    }

    public static <T, C extends Collection<T>> Pair<C, C> split(C source, Predicate<T> predicate, C falseCollection, C trueCollection) {
        for (T item : source)
            (predicate.test(item) ? trueCollection : falseCollection).add(item);
        return Pair.of(trueCollection, falseCollection);
    }

}
