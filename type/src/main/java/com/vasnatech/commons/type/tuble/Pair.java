package com.vasnatech.commons.type.tuble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.*;
import java.util.stream.Collectors;

public interface Pair<FIRST, SECOND> extends Tuple,  Map.Entry<FIRST, SECOND> {

    FIRST first();
    void first(FIRST newValue);
    Single<SECOND> removeFirst();

    SECOND second();
    void second(SECOND newValue);
    Single<FIRST> removeSecond();

    @Override
    default int length() {
        return 2;
    }

    @Override
    @SuppressWarnings("unchecked")
    default Object get(int index) {
        return switch (index) {
            case 0 -> first();
            case 1 -> second();
            default -> throw new IndexOutOfBoundsException(index);
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    default void set(int index, Object value) {
        if (index == 0) {
            first((FIRST) value);
        } else if (index == 1) {
            second((SECOND) value);
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    default Tuple remove(int index) {
        return switch (index) {
            case 0 -> removeFirst();
            case 1 -> removeSecond();
            default -> throw new IndexOutOfBoundsException(index);
        };
    }

    @Override
    default Object[] toArray() {
        return new Object[] {first(), second()};
    }

    @Override
    default List<?> toList() {
        return List.of(first(), second());
    }

    @Override
    <THIRD> Triple<FIRST, SECOND, THIRD> append(THIRD third);


    default <R> R apply(BiFunction<FIRST, SECOND, R> function) {
        return function.apply(first(), second());
    }
    default boolean test(BiPredicate<FIRST, SECOND> predicate) {
        return predicate.test(first(), second());
    }
    default void accept(BiConsumer<FIRST, SECOND> consumer) {
        consumer.accept(first(), second());
    }
    default void accept(Consumer<FIRST> firstConsumer, Consumer<SECOND> secondConsumer) {
        firstConsumer.accept(first());
        secondConsumer.accept(second());
    }


    @Override
    default FIRST getKey() {
        return first();
    }
    @Override
    default SECOND getValue() {
        return second();
    }


    default boolean equals(Pair<?, ?> that) {
        if (this == that) return true;
        if (that == null) return false;
        return Objects.equals(this.first(), that.first()) &&
               Objects.equals(this.second(), that.second());
    }


    static <FIRST, SECOND> Pair<FIRST, SECOND> of(FIRST first, SECOND second) {
        return immutable(first, second);
    }
    static <FIRST, SECOND> List<Pair<FIRST, SECOND>> of(FIRST first0, SECOND second0, FIRST first1, SECOND second1) {
        return List.of(immutable(first0, second0), immutable(first1, second1));
    }
    static <FIRST, SECOND> List<Pair<FIRST, SECOND>> of(FIRST first0, SECOND second0, FIRST first1, SECOND second1, FIRST first2, SECOND second2) {
        return List.of(immutable(first0, second0), immutable(first1, second1), immutable(first2, second2));
    }
    static <FIRST, SECOND> List<Pair<FIRST, SECOND>> of(FIRST first0, SECOND second0, FIRST first1, SECOND second1, FIRST first2, SECOND second2, FIRST first3, SECOND second3) {
        return List.of(immutable(first0, second0), immutable(first1, second1), immutable(first2, second2), immutable(first3, second3));
    }
    static <FIRST, SECOND> List<Pair<FIRST, SECOND>> of(FIRST first0, SECOND second0, FIRST first1, SECOND second1, FIRST first2, SECOND second2, FIRST first3, SECOND second3, FIRST first4, SECOND second4) {
        return List.of(immutable(first0, second0), immutable(first1, second1), immutable(first2, second2), immutable(first3, second3), immutable(first4, second4));
    }

    static <FIRST, SECOND> Immutable<FIRST, SECOND> immutable(FIRST first, SECOND second) {
        return new Immutable<>(first, second);
    }
    static <FIRST, SECOND> Mutable<FIRST, SECOND> mutable(FIRST first, SECOND second) {
        return new Mutable<>(first, second);
    }

    Pair<?, ?> EMPTY = Pair.of(null, null);
    @SuppressWarnings("unchecked")
    static <FIRST, SECOND> Pair<FIRST, SECOND> empty() {
        return (Pair<FIRST, SECOND>) EMPTY;
    }

    static <FIRST, SECOND, R> Function<Pair<FIRST, SECOND>, R> toFunction(BiFunction<FIRST, SECOND, R> function) {
        return pair -> function.apply(pair.first(), pair.second());
    }
    static <FIRST, SECOND, R> BiFunction<FIRST, SECOND, R> toBiFunction(Function<Pair<FIRST, SECOND>, R> function) {
        return (first, second) -> function.apply(Pair.of(first, second));
    }
    static <T, FIRST, SECOND> Function<T, Pair<FIRST, SECOND>> toFunction(Function<T, FIRST> toFirst, Function<T, SECOND> toSecond) {
        return t -> Pair.of(toFirst.apply(t), toSecond.apply(t));
    }

    static <FIRST, SECOND> Predicate<Pair<FIRST, SECOND>> toPredicate(BiPredicate<FIRST, SECOND> predicate) {
        return pair -> predicate.test(pair.first(), pair.second());
    }
    static <FIRST, SECOND> BiPredicate<FIRST, SECOND> toBiPredicate(Predicate<Pair<FIRST, SECOND>> predicate) {
        return (first, second) -> predicate.test(Pair.of(first, second));
    }

    static <FIRST, SECOND> Consumer<Pair<FIRST, SECOND>> toConsumer(BiConsumer<FIRST, SECOND> consumer) {
        return pair -> consumer.accept(pair.first(), pair.second());
    }
    static <FIRST, SECOND> BiConsumer<FIRST, SECOND> toBiConsumer(Consumer<Pair<FIRST, SECOND>> consumer) {
        return (first, second) -> consumer.accept(Pair.of(first, second));
    }

    static <FIRST1, FIRST2, SECOND1, SECOND2> Function<Pair<FIRST1, SECOND1>, Pair<FIRST2, SECOND2>> toPairFunction(
            Function<FIRST1, FIRST2> firstFunction,
            Function<SECOND1, SECOND2> secondFunction
    ) {
        return pair -> Pair.of(firstFunction.apply(pair.first()), secondFunction.apply(pair.second()));
    }

    static <FIRST1, FIRST2, SECOND> Function<Pair<FIRST1, SECOND>, Pair<FIRST2, SECOND>> toPairFunctionFirst(Function<FIRST1, FIRST2> function) {
        return toPairFunction(function, Function.identity());
    }

    static <FIRST, SECOND1, SECOND2> Function<Pair<FIRST, SECOND1>, Pair<FIRST, SECOND2>> toPairFunctionSecond(Function<SECOND1, SECOND2> function) {
        return toPairFunction(Function.identity(), function);
    }

    static <FIRST, SECOND> List<Pair<FIRST, SECOND>> zip(List<FIRST> firstList, List<SECOND> secondList) {
        ArrayList<Pair<FIRST, SECOND>> zippedList = new ArrayList<>(Math.min(firstList.size(), secondList.size()));
        Iterator<FIRST> firstIterator = firstList.iterator();
        Iterator<SECOND> secondIterator = secondList.iterator();
        while (firstIterator.hasNext() && secondIterator.hasNext()) {
            zippedList.add(of(firstIterator.next(), secondIterator.next()));
        }
        return Collections.unmodifiableList(zippedList);
    }

    static <FIRST, SECOND> Pair<List<FIRST>, List<SECOND>> unzip(List<Pair<FIRST, SECOND>> zippedList) {
        List<FIRST> firstList = new ArrayList<>(zippedList.size());
        List<SECOND> secondList = new ArrayList<>(zippedList.size());
        for (Pair<FIRST, SECOND> zipped : zippedList) {
            firstList.add(zipped.first());
            secondList.add(zipped.second());
        }
        return of(firstList, secondList);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND> List<Pair<FIRST, SECOND>> of(Object... objects) {
        ArrayList<Pair<FIRST, SECOND>> list = new ArrayList<>(objects.length / 2);
        int length = objects.length / 2 * 2;
        for (int index = 0; index < length; index += 2)
            list.add(of((FIRST) objects[index], (SECOND) objects[index + 1]));
        return List.copyOf(list);
    }
    @SuppressWarnings("unchecked")
    static <FIRST, SECOND> List<Pair<FIRST, SECOND>> of(List<?> objects) {
        ArrayList<Pair<FIRST, SECOND>> list = new ArrayList<>(objects.size() / 2);
        int length = objects.size() / 2 * 2;
        for (int index = 0; index < length; index += 2)
            list.add(of((FIRST) objects.get(index), (SECOND) objects.get(index + 1)));
        return List.copyOf(list);
    }

    static <FIRST, SECOND> List<Pair<FIRST, SECOND>> joinFirst(SECOND second, List<FIRST> firsts) {
        return firsts.stream().map(first -> of(first, second)).collect(Collectors.toList());
    }
    static <FIRST, SECOND> List<Pair<FIRST, SECOND>> joinSecond(FIRST first, List<SECOND> seconds) {
        return seconds.stream().map(second -> of(first, second)).collect(Collectors.toList());
    }
    static <FIRST, SECOND> Set<Pair<FIRST, SECOND>> joinFirst(SECOND second, Set<FIRST> firsts) {
        return firsts.stream().map(first -> of(first, second)).collect(Collectors.toSet());
    }
    static <FIRST, SECOND> Set<Pair<FIRST, SECOND>> joinSecond(FIRST first, Set<SECOND> seconds) {
        return seconds.stream().map(second -> of(first, second)).collect(Collectors.toSet());
    }

    static <FIRST, SECOND> Map<FIRST, List<SECOND>> groupByFirst(List<Pair<FIRST, SECOND>> pairs) {
        return pairs.stream().collect(
                Collectors.groupingBy(
                        Pair::first,
                        Collectors.mapping(Pair::second, Collectors.toList())
                )
        );
    }
    static <FIRST, SECOND> Map<SECOND, List<FIRST>> groupBySecond(List<Pair<FIRST, SECOND>> pairs) {
        return pairs.stream().collect(
                Collectors.groupingBy(
                        Pair::second,
                        Collectors.mapping(Pair::first, Collectors.toList())
                )
        );
    }



    final class Immutable<FIRST, SECOND> implements Pair<FIRST, SECOND> {
        final FIRST first;
        final SECOND second;

        Immutable(FIRST first, SECOND second) {
            this.first = first;
            this.second = second;
        }
        @Override
        public FIRST first() {
            return first;
        }
        @Override
        public void first(FIRST newValue) {
            throw new UnsupportedOperationException();
        }
        @Override
        public Single<SECOND> removeFirst() {
            return Single.immutable(second);
        }
        @Override
        public SECOND second() {
            return second;
        }
        @Override
        public void second(SECOND newValue) {
            throw new UnsupportedOperationException();
        }
        @Override
        public Single<FIRST> removeSecond() {
            return Single.immutable(first);
        }

        @Override
        public SECOND setValue(SECOND newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair<?, ?>)) return false;
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
        public <THIRD> Triple<FIRST, SECOND, THIRD> append(THIRD third) {
            return Triple.immutable(first, second, third);
        }
    }

    final class Mutable<FIRST, SECOND> implements Pair<FIRST, SECOND> {
        FIRST first;
        SECOND second;

        Mutable(FIRST first, SECOND second) {
            this.first = first;
            this.second = second;
        }
        @Override
        public FIRST first() {
            return first;
        }
        @Override
        public void first(FIRST newValue) {
            this.first = newValue;
        }
        @Override
        public Single<SECOND> removeFirst() {
            return Single.mutable(second);
        }
        @Override
        public SECOND second() {
            return second;
        }
        @Override
        public void second(SECOND newValue) {
            this.second = newValue;
        }
        @Override
        public Single<FIRST> removeSecond() {
            return Single.mutable(first);
        }

        @Override
        public SECOND setValue(SECOND newValue) {
            SECOND oldValue = this.second;
            this.second = newValue;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair<?, ?>)) return false;
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
        public <THIRD> Triple<FIRST, SECOND, THIRD> append(THIRD third) {
            return Triple.mutable(first, second, third);
        }
    }
}
