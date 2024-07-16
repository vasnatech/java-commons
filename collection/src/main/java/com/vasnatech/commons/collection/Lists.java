package com.vasnatech.commons.collection;

import com.vasnatech.commons.type.tuble.Pair;
import com.vasnatech.commons.function.TriFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Lists {

    private Lists() {}

    @SafeVarargs
    public static <T> List<T> ofNotNull(T... objects) {
        return Stream
                .of(objects)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> concat(List<? extends T>... lists) {
        return Stream.of(lists)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static <T> List<T> add(List<T> target, List<? extends T> source, int fromInclusive, int toExclusive) {
        if (target == null)
            target = new ArrayList<>(Math.max(0, toExclusive - fromInclusive));
        int index = fromInclusive;
        if (index < source.size()) {
            ListIterator<? extends T> iterator = source.listIterator(index);
            while (iterator.hasNext() && index++ < toExclusive)
                target.add(iterator.next());
        }
        return target;
    }

    public static <T, R> Function<List<T>, List<R>> mapper(Function<T, R> itemMapper) {
        return list -> map(list, itemMapper);
    }

    public static <T, R> List<R> map(List<T> source, Function<T, R> mapper) {
        return source == null
                ? List.of()
                : source.stream()
                        .map(mapper)
                        .collect(Collectors.toList());
    }

    public static <T, R> Function<List<T>, List<R>> mapperNotNull(Function<T, R> itemMapper) {
        return source -> mapNotNull(source, itemMapper);
    }

    public static <T, R> List<R> mapNotNull(List<T> source, Function<T, R> mapper) {
        return source == null
                ? List.of()
                : source.stream()
                        .filter(java.util.Objects::nonNull)
                        .map(mapper)
                        .filter(java.util.Objects::nonNull)
                        .collect(Collectors.toList());
    }

    public static <T, R> List<R> flatMap(List<T> source, Function<T, Collection<R>> func) {
        return source == null
                ? List.of()
                : source.stream()
                        .map(func)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
    }

    public static <T> List<T> filter(List<T> source, Predicate<T> filter) {
        return source == null
                ? List.of()
                : source.stream()
                        .filter(filter)
                        .collect(Collectors.toList());
    }

    public static <T> List<T> from(Iterable<T> iterable, int count) {
        return from(iterable.iterator(), count);
    }

    public static <T> List<T> from(Iterator<T> iterator, int count) {
        ArrayList<T> list = new ArrayList<>(count);
        int index = 0;
        while (iterator.hasNext() && index++ < count)
            list.add(iterator.next());
        return java.util.Collections.unmodifiableList(list);
    }

    public static <T> List<T> repeat(T item, int times) {
        return repeat(index -> item, times);
    }

    public static <T> List<T> repeat(Supplier<T> supplier, int times) {
        return repeat(index -> supplier.get(), times);
    }

    public static <T> List<T> repeat(IntFunction<T> function, int times) {
        return IntStream
                .range(0, Math.max(0, times))
                .mapToObj(function)
                .collect(Collectors.toList());
    }

    public static <T, U, R> List<R> zip(List<T> tList, List<U> uList, BiFunction<T, U, R> zipper) {
        ArrayList<R> zippedList = new ArrayList<>(Math.min(tList.size(), uList.size()));
        Iterator<T> tIterator = tList.iterator();
        Iterator<U> uIterator = uList.iterator();
        while (tIterator.hasNext() && uIterator.hasNext()) {
            zippedList.add(zipper.apply(tIterator.next(), uIterator.next()));
        }
        return java.util.Collections.unmodifiableList(zippedList);
    }

    public static <T, U, V, R> List<R> zip(List<T> tList, List<U> uList, List<V> vList, TriFunction<T, U, V, R> zipper) {
        ArrayList<R> zippedList = new ArrayList<>(Math.min(Math.min(tList.size(), uList.size()), vList.size()));
        Iterator<T> tIterator = tList.iterator();
        Iterator<U> uIterator = uList.iterator();
        Iterator<V> vIterator = vList.iterator();
        while (tIterator.hasNext() && uIterator.hasNext() && vIterator.hasNext()) {
            zippedList.add(zipper.apply(tIterator.next(), uIterator.next(), vIterator.next()));
        }
        return java.util.Collections.unmodifiableList(zippedList);
    }

    public static <T> Pair<List<T>, List<T>> split(List<T> source, Predicate<T> predicate) {
        return Collections.split(source, predicate, ArrayList::new);
    }

    public static <T> Pair<List<T>, List<T>> split(List<T> source, Predicate<T> predicate, Supplier<List<T>> listFactory) {
        return Collections.split(source, predicate, listFactory);
    }

    public static <T> Pair<List<T>, List<T>> split(List<T> source, Predicate<T> predicate, List<T> falseList, List<T> trueList) {
        return Collections.split(source, predicate, falseList, trueList);
    }

    public static List<?> deepCopy(List<?> source) {
        return source.stream().map(Objects::deepCopy).collect(Collectors.toList());
    }
}
