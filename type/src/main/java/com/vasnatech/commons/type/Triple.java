package com.vasnatech.commons.type;

import com.vasnatech.commons.function.TriConsumer;
import com.vasnatech.commons.function.TriFunction;
import com.vasnatech.commons.function.TriPredicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Triple<F, S, T> {

    F first();
    void first(F newValue);

    S second();
    void second(S newValue);

    T third();
    void third(T newValue);


    default <R> R apply(TriFunction<F, S, T, R> function) {
        return function.apply(first(), second(), third());
    }
    default boolean test(TriPredicate<F, S, T> predicate) {
        return predicate.test(first(), second(), third());
    }
    default void accept(TriConsumer<F, S, T> consumer) {
        consumer.accept(first(), second(), third());
    }
    default void accept(Consumer<F> firstConsumer, Consumer<S> secondConsumer, Consumer<T> thirdConsumer) {
        firstConsumer.accept(first());
        secondConsumer.accept(second());
        thirdConsumer.accept(third());
    }


    default Pair<F, S> toFirstAndSecond() {
        return Pair.of(first(), second());
    }
    default Pair<S, F> toSecondAndFirst() {
        return Pair.of(second(), first());
    }
    default Pair<F, T> toFirstAndThird() {
        return Pair.of(first(), third());
    }
    default Pair<T, F> toThirdAndFirst() {
        return Pair.of(third(), first());
    }
    default Pair<S, T> toSecondAndThird() {
        return Pair.of(second(), third());
    }
    default Pair<T, S> toThirdAndSecond() {
        return Pair.of(third(), second());
    }


    default boolean equals(Triple<?, ?, ?> that) {
        if (this == that) return true;
        if (that == null) return false;
        return Objects.equals(this.first(), that.first()) &&
               Objects.equals(this.second(), that.second()) &&
               Objects.equals(this.third(), that.third());
    }


    static <F, S, T> Immutable<F, S, T> immutable(F first, S second, T third) {
        return Immutable.of(first, second, third);
    }

    static <F, S, T> Mutable<F, S, T> mutable(F first, S second, T third) {
        return Mutable.of(first, second, third);
    }

    static <F, S, T> Triple<F, S, T> of(F f, S s, T t) {
        return immutable(f, s, t);
    }
    static <F, S, T> List<Triple<F, S, T>> of(
            F f0, S s0, T t0,
            F f1, S s1, T t1
    ) {
        return List.of(
                immutable(f0, s0, t0),
                immutable(f1, s1, t1)
        );
    }
    static <F, S, T> List<Triple<F, S, T>> of(
            F f0, S s0, T t0,
            F f1, S s1, T t1,
            F f2, S s2, T t2
    ) {
        return List.of(
                immutable(f0, s0, t0),
                immutable(f1, s1, t1),
                immutable(f2, s2, t2)
        );
    }
    static <F, S, T> List<Triple<F, S, T>> of(
            F f0, S s0, T t0,
            F f1, S s1, T t1,
            F f2, S s2, T t2,
            F f3, S s3, T t3
    ) {
        return List.of(
                immutable(f0, s0, t0),
                immutable(f1, s1, t1),
                immutable(f2, s2, t2),
                immutable(f3, s3, t3)
        );
    }
    static <F, S, T> List<Triple<F, S, T>> of(
            F f0, S s0, T t0,
            F f1, S s1, T t1,
            F f2, S s2, T t2,
            F f3, S s3, T t3,
            F f4, S s4, T t4
    ) {
        return List.of(
                immutable(f0, s0, t0),
                immutable(f1, s1, t1),
                immutable(f2, s2, t2),
                immutable(f3, s3, t3),
                immutable(f4, s4, t4)
        );
    }


    Triple<?, ?, ?> EMPTY = Triple.of(null, null, null);
    @SuppressWarnings("unchecked")
    static <F, S, T> Triple<F, S, T> empty() {
        return (Triple<F, S, T>) EMPTY;
    }

    static <F, S, T, R> Function<Triple<F, S, T>, R> toFunction(TriFunction<F, S, T, R> function) {
        return triple -> function.apply(triple.first(), triple.second(), triple.third());
    }
    static <F, S, T, R> TriFunction<F, S, T, R> toTriFunction(Function<Triple<F, S, T>, R> function) {
        return (first, second, third) -> function.apply(Triple.of(first, second, third));
    }
    static <R, F, S, T> Function<R, Triple<F, S, T>> toFunction(Function<R, F> toFirst, Function<R, S> toSecond, Function<R, T> toThird) {
        return r -> Triple.of(toFirst.apply(r), toSecond.apply(r), toThird.apply(r));
    }

    static <F, S, T> Predicate<Triple<F, S, T>> toPredicate(TriPredicate<F, S, T> predicate) {
        return triple -> predicate.test(triple.first(), triple.second(), triple.third());
    }
    static <F, S, T> TriPredicate<F, S, T> toTriPredicate(Predicate<Triple<F, S, T>> predicate) {
        return (first, second, third) -> predicate.test(Triple.of(first, second, third));
    }

    static <F, S, T> Consumer<Triple<F, S, T>> toConsumer(TriConsumer<F, S, T> consumer) {
        return triple -> consumer.accept(triple.first(), triple.second(), triple.third());
    }
    static <F, S, T> TriConsumer<F, S, T> toTriConsumer(Consumer<Triple<F, S, T>> consumer) {
        return (first, second, third) -> consumer.accept(Triple.of(first, second, third));
    }


    static <F, S, T> List<Triple<F, S, T>> zip(List<F> firstList, List<S> secondList, List<T> thirdList) {
        ArrayList<Triple<F, S, T>> zippedList = new ArrayList<>(Math.min(Math.min(firstList.size(), secondList.size()), thirdList.size()));
        Iterator<F> firstIterator = firstList.iterator();
        Iterator<S> secondIterator = secondList.iterator();
        Iterator<T> thirdIterator = thirdList.iterator();
        while (firstIterator.hasNext() && secondIterator.hasNext() && thirdIterator.hasNext()) {
            zippedList.add(of(firstIterator.next(), secondIterator.next(), thirdIterator.next()));
        }
        return List.copyOf(zippedList);
    }

    static <F, S, T> Triple<List<F>, List<S>, List<T>> unzip(List<Triple<F, S, T>> zippedList) {
        List<F> firstList = new ArrayList<>(zippedList.size());
        List<S> secondList = new ArrayList<>(zippedList.size());
        List<T> thirdList = new ArrayList<>(zippedList.size());
        for (Triple<F, S, T> zipped : zippedList) {
            firstList.add(zipped.first());
            secondList.add(zipped.second());
            thirdList.add(zipped.third());
        }
        return of(firstList, secondList, thirdList);
    }

    @SuppressWarnings("unchecked")
    static <F, S, T> List<Triple<F, S, T>> of(Object... objects) {
        ArrayList<Triple<F, S, T>> list = new ArrayList<>(objects.length / 3);
        int length = objects.length / 3 * 3;
        for (int index = 0; index < length; index += 3)
            list.add(of((F)objects[index], (S)objects[index + 1], (T)objects[index + 2]));
        return List.copyOf(list);
    }
    @SuppressWarnings("unchecked")
    static <F, S, T> List<Triple<F, S, T>> of(List<?> objects) {
        ArrayList<Triple<F, S, T>> list = new ArrayList<>(objects.size() / 3);
        int length = objects.size() / 3 * 3;
        for (int index = 0; index < length; index += 3)
            list.add(of((F)objects.get(index), (S)objects.get(index + 1), (T)objects.get(index + 2)));
        return List.copyOf(list);
    }

    static <F, S, T> List<Triple<F, S, T>> joinFirst(S second, T third, List<F> firsts) {
        return firsts.stream().map(first -> of(first, second, third)).collect(Collectors.toList());
    }
    static <F, S, T> List<Triple<F, S, T>> joinSecond(F first, T third, List<S> seconds) {
        return seconds.stream().map(second -> of(first, second, third)).collect(Collectors.toList());
    }
    static <F, S, T> List<Triple<F, S, T>> joinThird(F first, S second, List<T> thirds) {
        return thirds.stream().map(third -> of(first, second, third)).collect(Collectors.toList());
    }

    static <F, S, T> Map<F, List<Pair<S, T>>> groupByFirst(List<Triple<F, S, T>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::first,
                        Collectors.mapping(Triple::toSecondAndThird, Collectors.toList())
                )
        );
    }
    static <F, S, T> Map<S, List<Pair<F, T>>> groupBySecond(List<Triple<F, S, T>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::second,
                        Collectors.mapping(Triple::toFirstAndThird, Collectors.toList())
                )
        );
    }
    static <F, S, T> Map<T, List<Pair<F, S>>> groupByThird(List<Triple<F, S, T>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::third,
                        Collectors.mapping(Triple::toFirstAndSecond, Collectors.toList())
                )
        );
    }

    static <F, S, T> Map<F, Map<S, List<T>>> groupByFirstAndSecond(List<Triple<F, S, T>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::first,
                        Collectors.groupingBy(
                                Triple::second,
                                Collectors.mapping(Triple::third, Collectors.toList())
                        )
                )
        );
    }
    static <F, S, T> Map<S, Map<F, List<T>>> groupBySecondAndFirst(List<Triple<F, S, T>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::second,
                        Collectors.groupingBy(
                                Triple::first,
                                Collectors.mapping(Triple::third, Collectors.toList())
                        )
                )
        );
    }
    static <F, S, T> Map<S, Map<T, List<F>>> groupBySecondAndThird(List<Triple<F, S, T>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::second,
                        Collectors.groupingBy(
                                Triple::third,
                                Collectors.mapping(Triple::first, Collectors.toList())
                        )
                )
        );
    }
    static <F, S, T> Map<T, Map<S, List<F>>> groupByThirdAndSecond(List<Triple<F, S, T>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::third,
                        Collectors.groupingBy(
                                Triple::second,
                                Collectors.mapping(Triple::first, Collectors.toList())
                        )
                )
        );
    }
    static <F, S, T> Map<T, Map<F, List<S>>> groupByThirdAndFirst(List<Triple<F, S, T>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::third,
                        Collectors.groupingBy(
                                Triple::first,
                                Collectors.mapping(Triple::second, Collectors.toList())
                        )
                )
        );
    }
    static <F, S, T> Map<F, Map<T, List<S>>> groupByFirstAndThird(List<Triple<F, S, T>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::first,
                        Collectors.groupingBy(
                                Triple::third,
                                Collectors.mapping(Triple::second, Collectors.toList())
                        )
                )
        );
    }


    final class Immutable<F, S, T> implements Triple<F, S, T> {
        F first;
        S second;
        T third;

        Immutable(F first, S second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
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
        public T third() {
            return third;
        }
        @Override
        public void third(T newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            return equals((Triple<?, ?, ?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first, second, third);
        }

        transient String toStringCache = null;
        @Override
        public String toString() {
            if (toStringCache == null)
                toStringCache = new StringJoiner(",", "(", ")").add(String.valueOf(first)).add(String.valueOf(second)).add(String.valueOf(third)).toString();
            return toStringCache;
        }

        public static <F, S, T> Immutable<F, S, T> of(F first, S second, T third) {
            return new Immutable<>(first, second, third);
        }
    }

    final class Mutable<F, S, T> implements Triple<F, S, T> {
        F first;
        S second;
        T third;

        Mutable(F first, S second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
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
        public T third() {
            return third;
        }
        @Override
        public void third(T newValue) {
            this.third = newValue;
        }

        @Override
        public boolean equals(Object o) {
            return equals((Triple<?, ?, ?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first, second, third);
        }

        @Override
        public String toString() {
            return new StringJoiner(",", "(", ")").add(String.valueOf(first)).add(String.valueOf(second)).add(String.valueOf(third)).toString();
        }

        public static <F, S, T> Mutable<F, S, T> of(F first, S second, T third) {
            return new Mutable<>(first, second, third);
        }
    }
}