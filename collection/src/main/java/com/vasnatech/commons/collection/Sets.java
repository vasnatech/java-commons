package com.vasnatech.commons.collection;

import com.vasnatech.commons.type.tuble.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Sets {

    private Sets() {}

    @SafeVarargs
    public static <T> Set<T> ofNotNull(T... objects) {
        return Stream.of(objects).filter(java.util.Objects::nonNull).collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> concat(Set<? extends T>... sets) {
        return Stream.of(sets)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public static <T, R> Set<R> map(Set<T> source, Function<T, R> mapper) {
        return source.stream().map(mapper).collect(Collectors.toSet());
    }

    public static <T> Set<T> filter(Set<T> source, Predicate<T> filter) {
        return source.stream().filter(filter).collect(Collectors.toSet());
    }

    public static <T> Pair<Set<T>, Set<T>> split(Set<T> source, Predicate<T> predicate) {
        return Collections.split(source, predicate, HashSet::new);
    }

    public static <T> Pair<Set<T>, Set<T>> split(Set<T> source, Predicate<T> predicate, Supplier<Set<T>> setFactory) {
        return Collections.split(source, predicate, setFactory);
    }

    public static <T> Pair<Set<T>, Set<T>> split(Set<T> source, Predicate<T> predicate, Set<T> falseSet, Set<T> trueSet) {
        return Collections.split(source, predicate, falseSet, trueSet);
    }

    public static Set<?> deepCopy(Set<?> source) {
        return source.stream().map(Objects::deepCopy).collect(Collectors.toSet());
    }
}