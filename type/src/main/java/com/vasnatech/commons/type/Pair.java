package com.vasnatech.commons.type;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public interface Pair<F, S> extends Map.Entry<F, S> {

    F first();
    void first(F newValue);

    S second();
    void second(S newValue);


    <T> Triple<F, S, T> append(T third);


    default <R> R apply(BiFunction<F, S, R> function) {
        return function.apply(first(), second());
    }
    default boolean test(BiPredicate<F, S> predicate) {
        return predicate.test(first(), second());
    }
    default void accept(BiConsumer<F, S> consumer) {
        consumer.accept(first(), second());
    }
    default void accept(Consumer<F> firstConsumer, Consumer<S> secondConsumer) {
        firstConsumer.accept(first());
        secondConsumer.accept(second());
    }


    @Override
    default F getKey() {
        return first();
    }
    @Override
    default S getValue() {
        return second();
    }


    default boolean equals(Pair<?, ?> that) {
        if (this == that) return true;
        if (that == null) return false;
        return Objects.equals(this.first(), that.first()) &&
               Objects.equals(this.second(), that.second());
    }


    static <F, S> Pair<F, S> of(F first, S second) {
        return immutable(first, second);
    }
    static <F, S> List<Pair<F, S>> of(F f0, S s0, F f1, S s1) {
        return List.of(immutable(f0, s0), immutable(f1, s1));
    }
    static <F, S> List<Pair<F, S>> of(F f0, S s0, F f1, S s1, F f2, S s2) {
        return List.of(immutable(f0, s0), immutable(f1, s1), immutable(f2, s2));
    }
    static <F, S> List<Pair<F, S>> of(F f0, S s0, F f1, S s1, F f2, S s2, F f3, S s3) {
        return List.of(immutable(f0, s0), immutable(f1, s1), immutable(f2, s2), immutable(f3, s3));
    }
    static <F, S> List<Pair<F, S>> of(F f0, S s0, F f1, S s1, F f2, S s2, F f3, S s3, F f4, S s4) {
        return List.of(immutable(f0, s0), immutable(f1, s1), immutable(f2, s2), immutable(f3, s3), immutable(f4, s4));
    }

    static <F, S> Immutable<F, S> immutable(F first, S second) {
        return Immutable.of(first, second);
    }
    static <F, S> Mutable<F, S> mutable(F first, S second) {
        return Mutable.of(first, second);
    }

    Pair<?, ?> EMPTY = Pair.of(null, null);
    @SuppressWarnings("unchecked")
    static <F, S> Pair<F, S> empty() {
        return (Pair<F, S>) EMPTY;
    }

    static <F, S, R> Function<Pair<F, S>, R> toFunction(BiFunction<F, S, R> function) {
        return pair -> function.apply(pair.first(), pair.second());
    }
    static <F, S, R> BiFunction<F, S, R> toBiFunction(Function<Pair<F, S>, R> function) {
        return (first, second) -> function.apply(Pair.of(first, second));
    }
    static <T, F, S> Function<T, Pair<F, S>> toFunction(Function<T, F> toFirst, Function<T, S> toSecond) {
        return t -> Pair.of(toFirst.apply(t), toSecond.apply(t));
    }

    static <F, S> Predicate<Pair<F, S>> toPredicate(BiPredicate<F, S> predicate) {
        return pair -> predicate.test(pair.first(), pair.second());
    }
    static <F, S> BiPredicate<F, S> toBiPredicate(Predicate<Pair<F, S>> predicate) {
        return (first, second) -> predicate.test(Pair.of(first, second));
    }

    static <F, S> Consumer<Pair<F, S>> toConsumer(BiConsumer<F, S> consumer) {
        return pair -> consumer.accept(pair.first(), pair.second());
    }
    static <F, S> BiConsumer<F, S> toBiConsumer(Consumer<Pair<F, S>> consumer) {
        return (first, second) -> consumer.accept(Pair.of(first, second));
    }

    static <F, S> List<Pair<F, S>> zip(List<F> firstList, List<S> secondList) {
        ArrayList<Pair<F, S>> zippedList = new ArrayList<>(Math.min(firstList.size(), secondList.size()));
        Iterator<F> firstIterator = firstList.iterator();
        Iterator<S> secondIterator = secondList.iterator();
        while (firstIterator.hasNext() && secondIterator.hasNext()) {
            zippedList.add(of(firstIterator.next(), secondIterator.next()));
        }
        return List.copyOf(zippedList);
    }

    static <F, S> Pair<List<F>, List<S>> unzip(List<Pair<F, S>> zippedList) {
        List<F> firstList = new ArrayList<>(zippedList.size());
        List<S> secondList = new ArrayList<>(zippedList.size());
        for (Pair<F, S> zipped : zippedList) {
            firstList.add(zipped.first());
            secondList.add(zipped.second());
        }
        return of(firstList, secondList);
    }

    @SuppressWarnings("unchecked")
    static <F, S> List<Pair<F, S>> of(Object... objects) {
        ArrayList<Pair<F, S>> list = new ArrayList<>(objects.length / 2);
        int length = objects.length / 2 * 2;
        for (int index = 0; index < length; index += 2)
            list.add(of((F)objects[index], (S)objects[index + 1]));
        return List.copyOf(list);
    }
    @SuppressWarnings("unchecked")
    static <F, S> List<Pair<F, S>> of(List<?> objects) {
        ArrayList<Pair<F, S>> list = new ArrayList<>(objects.size() / 2);
        int length = objects.size() / 2 * 2;
        for (int index = 0; index < length; index += 2)
            list.add(of((F)objects.get(index), (S)objects.get(index + 1)));
        return List.copyOf(list);
    }

    static <F, S> List<Pair<F, S>> joinFirst(S second, List<F> firsts) {
        return firsts.stream().map(first -> of(first, second)).collect(Collectors.toList());
    }
    static <F, S> List<Pair<F, S>> joinSecond(F first, List<S> seconds) {
        return seconds.stream().map(second -> of(first, second)).collect(Collectors.toList());
    }
    static <F, S> Set<Pair<F, S>> joinFirst(S second, Set<F> firsts) {
        return firsts.stream().map(first -> of(first, second)).collect(Collectors.toSet());
    }
    static <F, S> Set<Pair<F, S>> joinSecond(F first, Set<S> seconds) {
        return seconds.stream().map(second -> of(first, second)).collect(Collectors.toSet());
    }

    static <F, S> Map<F, List<S>> groupByFirst(List<Pair<F, S>> pairs) {
        return pairs.stream().collect(
                Collectors.groupingBy(
                        Pair::first,
                        Collectors.mapping(Pair::second, Collectors.toList())
                )
        );
    }
    static <F, S> Map<S, List<F>> groupBySecond(List<Pair<F, S>> pairs) {
        return pairs.stream().collect(
                Collectors.groupingBy(
                        Pair::second,
                        Collectors.mapping(Pair::first, Collectors.toList())
                )
        );
    }



    final class Immutable<F, S> implements Pair<F, S> {
        F first;
        S second;

        Immutable(F first, S second) {
            this.first = first;
            this.second = second;
        }
        @Override
        public F first() {
            return first;
        }
        @Override
        public void first(F newValue) {
            throw new UnsupportedOperationException();
        }
        @Override
        public S second() {
            return second;
        }
        @Override
        public void second(S newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public S setValue(S newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            return equals((Pair<?, ?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        transient String toStringCache = null;
        @Override
        public String toString() {
            if (toStringCache == null)
                toStringCache = new StringJoiner(",", "(", ")").add(String.valueOf(first)).add(String.valueOf(second)).toString();
            return toStringCache;
        }

        @Override
        public <T> Triple<F, S, T> append(T third) {
            return Triple.immutable(first, second, third);
        }

        public static <F, S> Immutable<F, S> of(F first, S second) {
            return new Immutable<>(first, second);
        }
    }

    final class Mutable<F, S> implements Pair<F, S> {
        F first;
        S second;

        Mutable(F first, S second) {
            this.first = first;
            this.second = second;
        }
        @Override
        public F first() {
            return first;
        }
        @Override
        public void first(F newValue) {
            this.first = newValue;
        }
        @Override
        public S second() {
            return second;
        }
        @Override
        public void second(S newValue) {
            this.second = newValue;
        }

        @Override
        public S setValue(S newValue) {
            S oldValue = this.second;
            this.second = newValue;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            return equals((Pair<?, ?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public String toString() {
            return new StringJoiner(",", "(", ")").add(String.valueOf(first)).add(String.valueOf(second)).toString();
        }

        @Override
        public <T> Triple<F, S, T> append(T third) {
            return Triple.mutable(first, second, third);
        }

        public static <F, S> Mutable<F, S> of(F first, S second) {
            return new Mutable<>(first, second);
        }
    }
}
