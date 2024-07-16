package com.vasnatech.commons.type.tuple;

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

public interface Triple<FIRST, SECOND, THIRD> extends Tuple {

    FIRST first();
    void first(FIRST newValue);
    Pair<SECOND, THIRD> removeFirst();

    SECOND second();
    void second(SECOND newValue);
    Pair<FIRST, THIRD> removeSecond();

    THIRD third();
    void third(THIRD newValue);
    Pair<FIRST, SECOND> removeThird();

    @Override
    @SuppressWarnings("unchecked")
    default FIRST head() {
        return first();
    }
    @Override
    @SuppressWarnings("unchecked")
    default THIRD tail() {
        return third();
    }

    default Pair<SECOND, THIRD> removeHead() {
        return removeFirst();
    }
    default Pair<FIRST, SECOND> removeTail() {
        return removeThird();
    }

    @Override
    default int length() {
        return 3;
    }

    @Override
    @SuppressWarnings("unchecked")
    default Object get(int index) {
        return switch (index) {
            case 0 -> first();
            case 1 -> second();
            case 2 -> third();
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
        } else if (index == 2) {
            third((THIRD) value);
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    default Tuple remove(int index) {
        return switch (index) {
            case 0 -> removeFirst();
            case 1 -> removeSecond();
            case 2 -> removeThird();
            default -> throw new IndexOutOfBoundsException(index);
        };
    }

    @Override
    default Object[] toArray() {
        return new Object[] {first(), second(), third()};
    }

    @Override
    default List<?> toList() {
        return List.of(first(), second(), third());
    }

    @Override
    <FOURTH> Quadruple<FIRST, SECOND, THIRD, FOURTH> append(FOURTH fourth);


    default <R> R apply(TriFunction<FIRST, SECOND, THIRD, R> function) {
        return function.apply(first(), second(), third());
    }
    default boolean test(TriPredicate<FIRST, SECOND, THIRD> predicate) {
        return predicate.test(first(), second(), third());
    }
    default void accept(TriConsumer<FIRST, SECOND, THIRD> consumer) {
        consumer.accept(first(), second(), third());
    }
    default void accept(Consumer<FIRST> firstConsumer, Consumer<SECOND> secondConsumer, Consumer<THIRD> thirdConsumer) {
        firstConsumer.accept(first());
        secondConsumer.accept(second());
        thirdConsumer.accept(third());
    }


    default boolean equals(Triple<?, ?, ?> that) {
        if (this == that) return true;
        if (that == null) return false;
        return Objects.equals(this.first(), that.first()) &&
               Objects.equals(this.second(), that.second()) &&
               Objects.equals(this.third(), that.third());
    }


    static <FIRST, SECOND, THIRD> Immutable<FIRST, SECOND, THIRD> immutable(FIRST first, SECOND second, THIRD third) {
        return new Immutable<>(first, second, third);
    }

    static <FIRST, SECOND, THIRD> Mutable<FIRST, SECOND, THIRD> mutable(FIRST first, SECOND second, THIRD third) {
        return new Mutable<>(first, second, third);
    }

    static <FIRST, SECOND, THIRD> Triple<FIRST, SECOND, THIRD> of(FIRST first, SECOND second, THIRD third) {
        return immutable(first, second, third);
    }
    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> of(
            FIRST first0, SECOND second0, THIRD third0,
            FIRST first1, SECOND second1, THIRD third1
    ) {
        return List.of(
                immutable(first0, second0, third0),
                immutable(first1, second1, third1)
        );
    }
    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> of(
            FIRST first0, SECOND second0, THIRD third0,
            FIRST first1, SECOND second1, THIRD third1,
            FIRST first2, SECOND second2, THIRD third2
    ) {
        return List.of(
                immutable(first0, second0, third0),
                immutable(first1, second1, third1),
                immutable(first2, second2, third2)
        );
    }
    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> of(
            FIRST first0, SECOND second0, THIRD third0,
            FIRST first1, SECOND second1, THIRD third1,
            FIRST first2, SECOND second2, THIRD third2,
            FIRST first3, SECOND second3, THIRD third3
    ) {
        return List.of(
                immutable(first0, second0, third0),
                immutable(first1, second1, third1),
                immutable(first2, second2, third2),
                immutable(first3, second3, third3)
        );
    }
    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> of(
            FIRST first0, SECOND second0, THIRD third0,
            FIRST first1, SECOND second1, THIRD third1,
            FIRST first2, SECOND second2, THIRD third2,
            FIRST first3, SECOND second3, THIRD third3,
            FIRST first4, SECOND second4, THIRD third4
    ) {
        return List.of(
                immutable(first0, second0, third0),
                immutable(first1, second1, third1),
                immutable(first2, second2, third2),
                immutable(first3, second3, third3),
                immutable(first4, second4, third4)
        );
    }


    Triple<?, ?, ?> EMPTY = Triple.of(null, null, null);
    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD> Triple<FIRST, SECOND, THIRD> empty() {
        return (Triple<FIRST, SECOND, THIRD>) EMPTY;
    }

    static <FIRST, SECOND, THIRD, R> Function<Triple<FIRST, SECOND, THIRD>, R> toFunction(TriFunction<FIRST, SECOND, THIRD, R> function) {
        return triple -> function.apply(triple.first(), triple.second(), triple.third());
    }
    static <FIRST, SECOND, THIRD, R> TriFunction<FIRST, SECOND, THIRD, R> toTriFunction(Function<Triple<FIRST, SECOND, THIRD>, R> function) {
        return (first, second, third) -> function.apply(Triple.of(first, second, third));
    }
    static <FIRST, SECOND, THIRD, R> Function<R, Triple<FIRST, SECOND, THIRD>> toFunction(Function<R, FIRST> toFirst, Function<R, SECOND> toSecond, Function<R, THIRD> toThird) {
        return r -> Triple.of(toFirst.apply(r), toSecond.apply(r), toThird.apply(r));
    }

    static <FIRST, SECOND, THIRD> Predicate<Triple<FIRST, SECOND, THIRD>> toPredicate(TriPredicate<FIRST, SECOND, THIRD> predicate) {
        return triple -> predicate.test(triple.first(), triple.second(), triple.third());
    }
    static <FIRST, SECOND, THIRD> TriPredicate<FIRST, SECOND, THIRD> toTriPredicate(Predicate<Triple<FIRST, SECOND, THIRD>> predicate) {
        return (first, second, third) -> predicate.test(Triple.of(first, second, third));
    }

    static <FIRST, SECOND, THIRD> Consumer<Triple<FIRST, SECOND, THIRD>> toConsumer(TriConsumer<FIRST, SECOND, THIRD> consumer) {
        return triple -> consumer.accept(triple.first(), triple.second(), triple.third());
    }
    static <FIRST, SECOND, THIRD> TriConsumer<FIRST, SECOND, THIRD> toTriConsumer(Consumer<Triple<FIRST, SECOND, THIRD>> consumer) {
        return (first, second, third) -> consumer.accept(Triple.of(first, second, third));
    }


    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> zip(List<FIRST> firstList, List<SECOND> secondList, List<THIRD> thirdList) {
        ArrayList<Triple<FIRST, SECOND, THIRD>> zippedList = new ArrayList<>(Math.min(Math.min(firstList.size(), secondList.size()), thirdList.size()));
        Iterator<FIRST> firstIterator = firstList.iterator();
        Iterator<SECOND> secondIterator = secondList.iterator();
        Iterator<THIRD> thirdIterator = thirdList.iterator();
        while (firstIterator.hasNext() && secondIterator.hasNext() && thirdIterator.hasNext()) {
            zippedList.add(of(firstIterator.next(), secondIterator.next(), thirdIterator.next()));
        }
        return List.copyOf(zippedList);
    }

    static <FIRST, SECOND, THIRD> Triple<List<FIRST>, List<SECOND>, List<THIRD>> unzip(List<Triple<FIRST, SECOND, THIRD>> zippedList) {
        List<FIRST> firstList = new ArrayList<>(zippedList.size());
        List<SECOND> secondList = new ArrayList<>(zippedList.size());
        List<THIRD> thirdList = new ArrayList<>(zippedList.size());
        for (Triple<FIRST, SECOND, THIRD> zipped : zippedList) {
            firstList.add(zipped.first());
            secondList.add(zipped.second());
            thirdList.add(zipped.third());
        }
        return of(firstList, secondList, thirdList);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> of(Object... objects) {
        ArrayList<Triple<FIRST, SECOND, THIRD>> list = new ArrayList<>(objects.length / 3);
        int length = objects.length / 3 * 3;
        for (int index = 0; index < length; index += 3)
            list.add(of((FIRST) objects[index], (SECOND) objects[index + 1], (THIRD) objects[index + 2]));
        return List.copyOf(list);
    }
    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> of(List<?> objects) {
        ArrayList<Triple<FIRST, SECOND, THIRD>> list = new ArrayList<>(objects.size() / 3);
        int length = objects.size() / 3 * 3;
        for (int index = 0; index < length; index += 3)
            list.add(of((FIRST) objects.get(index), (SECOND) objects.get(index + 1), (THIRD) objects.get(index + 2)));
        return List.copyOf(list);
    }

    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> joinFirst(SECOND second, THIRD third, List<FIRST> firsts) {
        return firsts.stream().map(first -> of(first, second, third)).collect(Collectors.toList());
    }
    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> joinSecond(FIRST first, THIRD third, List<SECOND> seconds) {
        return seconds.stream().map(second -> of(first, second, third)).collect(Collectors.toList());
    }
    static <FIRST, SECOND, THIRD> List<Triple<FIRST, SECOND, THIRD>> joinThird(FIRST first, SECOND second, List<THIRD> thirds) {
        return thirds.stream().map(third -> of(first, second, third)).collect(Collectors.toList());
    }

    static <FIRST, SECOND, THIRD> Map<FIRST, List<Pair<SECOND, THIRD>>> groupByFirst(List<Triple<FIRST, SECOND, THIRD>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::first,
                        Collectors.mapping(Triple::removeFirst, Collectors.toList())
                )
        );
    }
    static <FIRST, SECOND, THIRD> Map<SECOND, List<Pair<FIRST, THIRD>>> groupBySecond(List<Triple<FIRST, SECOND, THIRD>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::second,
                        Collectors.mapping(Triple::removeSecond, Collectors.toList())
                )
        );
    }
    static <FIRST, SECOND, THIRD> Map<THIRD, List<Pair<FIRST, SECOND>>> groupByThird(List<Triple<FIRST, SECOND, THIRD>> triples) {
        return triples.stream().collect(
                Collectors.groupingBy(
                        Triple::third,
                        Collectors.mapping(Triple::removeThird, Collectors.toList())
                )
        );
    }

    static <FIRST, SECOND, THIRD> Map<FIRST, Map<SECOND, List<THIRD>>> groupByFirstAndSecond(List<Triple<FIRST, SECOND, THIRD>> triples) {
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
    static <FIRST, SECOND, THIRD> Map<SECOND, Map<FIRST, List<THIRD>>> groupBySecondAndFirst(List<Triple<FIRST, SECOND, THIRD>> triples) {
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
    static <FIRST, SECOND, THIRD> Map<SECOND, Map<THIRD, List<FIRST>>> groupBySecondAndThird(List<Triple<FIRST, SECOND, THIRD>> triples) {
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
    static <FIRST, SECOND, THIRD> Map<THIRD, Map<SECOND, List<FIRST>>> groupByThirdAndSecond(List<Triple<FIRST, SECOND, THIRD>> triples) {
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
    static <FIRST, SECOND, THIRD> Map<THIRD, Map<FIRST, List<SECOND>>> groupByThirdAndFirst(List<Triple<FIRST, SECOND, THIRD>> triples) {
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
    static <FIRST, SECOND, THIRD> Map<FIRST, Map<THIRD, List<SECOND>>> groupByFirstAndThird(List<Triple<FIRST, SECOND, THIRD>> triples) {
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


    final class Immutable<FIRST, SECOND, THIRD> implements Triple<FIRST, SECOND, THIRD> {
        final FIRST first;
        final SECOND second;
        final THIRD third;

        Immutable(FIRST first, SECOND second, THIRD third) {
            this.first = first;
            this.second = second;
            this.third = third;
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
        public Pair<SECOND, THIRD> removeFirst() {
            return Pair.immutable(second, third);
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
        public Pair<FIRST, THIRD> removeSecond() {
            return Pair.immutable(first, third);
        }
        @Override
        public THIRD third() {
            return third;
        }
        @Override
        public void third(THIRD newValue) {
            throw new UnsupportedOperationException();
        }
        @Override
        public Pair<FIRST, SECOND> removeThird() {
            return Pair.immutable(first, second);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Triple<?, ?, ?>)) return false;
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

        @Override
        public <FOURTH> Quadruple<FIRST, SECOND, THIRD, FOURTH> append(FOURTH fourth) {
            return Quadruple.immutable(first, second, third, fourth);
        }
    }

    final class Mutable<FIRST, SECOND, THIRD> implements Triple<FIRST, SECOND, THIRD> {
        FIRST first;
        SECOND second;
        THIRD third;

        Mutable(FIRST first, SECOND second, THIRD third) {
            this.first = first;
            this.second = second;
            this.third = third;
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
        public Pair<SECOND, THIRD> removeFirst() {
            return Pair.mutable(second, third);
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
        public Pair<FIRST, THIRD> removeSecond() {
            return Pair.mutable(first, third);
        }
        @Override
        public THIRD third() {
            return third;
        }
        @Override
        public void third(THIRD newValue) {
            this.third = newValue;
        }
        @Override
        public Pair<FIRST, SECOND> removeThird() {
            return Pair.mutable(first, second);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Triple<?, ?, ?>)) return false;
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

        @Override
        public <FOURTH> Quadruple<FIRST, SECOND, THIRD, FOURTH> append(FOURTH fourth) {
            return Quadruple.mutable(first, second, third, fourth);
        }
    }
}