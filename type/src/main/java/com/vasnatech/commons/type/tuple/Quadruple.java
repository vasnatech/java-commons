package com.vasnatech.commons.type.tuple;

import com.vasnatech.commons.function.*;

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

public interface Quadruple<FIRST, SECOND, THIRD, FOURTH> extends Tuple {

    FIRST first();
    void first(FIRST newValue);
    Triple<SECOND, THIRD, FOURTH> removeFirst();

    SECOND second();
    void second(SECOND newValue);
    Triple<FIRST, THIRD, FOURTH> removeSecond();

    THIRD third();
    void third(THIRD newValue);
    Triple<FIRST, SECOND, FOURTH> removeThird();

    FOURTH fourth();
    void fourth(FOURTH newValue);
    Triple<FIRST, SECOND, THIRD> removeFourth();

    @Override
    @SuppressWarnings("unchecked")
    default FIRST head() {
        return first();
    }
    @Override
    @SuppressWarnings("unchecked")
    default FOURTH tail() {
        return fourth();
    }

    default Triple<SECOND, THIRD, FOURTH> removeHead() {
        return removeFirst();
    }
    default Triple<FIRST, SECOND, THIRD> removeTail() {
        return removeFourth();
    }

    @Override
    default int length() {
        return 4;
    }

    @Override
    @SuppressWarnings("unchecked")
    default Object get(int index) {
        return switch (index) {
            case 0 -> first();
            case 1 -> second();
            case 2 -> third();
            case 3 -> fourth();
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
        } else if (index == 3) {
            fourth((FOURTH) value);
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
            case 3 -> removeFourth();
            default -> throw new IndexOutOfBoundsException(index);
        };
    }

    @Override
    default Object[] toArray() {
        return new Object[] {first(), second(), third(), fourth()};
    }

    @Override
    default List<?> toList() {
        return List.of(first(), second(), third(), fourth());
    }

    @Override
    <FIFTH> Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> append(FIFTH fifth);


    default <R> R apply(TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> function) {
        return function.apply(first(), second(), third(), fourth());
    }
    default boolean test(TetraPredicate<FIRST, SECOND, THIRD, FOURTH> predicate) {
        return predicate.test(first(), second(), third(), fourth());
    }
    default void accept(TetraConsumer<FIRST, SECOND, THIRD, FOURTH> consumer) {
        consumer.accept(first(), second(), third(), fourth());
    }
    default void accept(Consumer<FIRST> firstConsumer, Consumer<SECOND> secondConsumer, Consumer<THIRD> thirdConsumer, Consumer<FOURTH> fourthConsumer) {
        firstConsumer.accept(first());
        secondConsumer.accept(second());
        thirdConsumer.accept(third());
        fourthConsumer.accept(fourth());
    }


    default boolean equals(Quadruple<?, ?, ?, ?> that) {
        if (this == that) return true;
        if (that == null) return false;
        return Objects.equals(this.first(), that.first()) &&
               Objects.equals(this.second(), that.second()) &&
               Objects.equals(this.third(), that.third()) &&
               Objects.equals(this.fourth(), that.fourth());
    }


    static <FIRST, SECOND, THIRD, FOURTH> Immutable<FIRST, SECOND, THIRD, FOURTH> immutable(FIRST first, SECOND second, THIRD third, FOURTH fourth) {
        return new Immutable<>(first, second, third, fourth);
    }

    static <FIRST, SECOND, THIRD, FOURTH> Mutable<FIRST, SECOND, THIRD, FOURTH> mutable(FIRST first, SECOND second, THIRD third, FOURTH fourth) {
        return new Mutable<>(first, second, third, fourth);
    }

    static <FIRST, SECOND, THIRD, FOURTH> Quadruple<FIRST, SECOND, THIRD, FOURTH> of(FIRST first, SECOND second, THIRD third, FOURTH fourth) {
        return immutable(first, second, third, fourth);
    }
    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> of(
            FIRST first0, SECOND second0, THIRD third0, FOURTH fourth0,
            FIRST first1, SECOND second1, THIRD third1, FOURTH fourth1
    ) {
        return List.of(
                immutable(first0, second0, third0, fourth0),
                immutable(first1, second1, third1, fourth1)
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> of(
            FIRST first0, SECOND second0, THIRD third0, FOURTH fourth0,
            FIRST first1, SECOND second1, THIRD third1, FOURTH fourth1,
            FIRST first2, SECOND second2, THIRD third2, FOURTH fourth2
    ) {
        return List.of(
                immutable(first0, second0, third0, fourth0),
                immutable(first1, second1, third1, fourth1),
                immutable(first2, second2, third2, fourth2)
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> of(
            FIRST first0, SECOND second0, THIRD third0, FOURTH fourth0,
            FIRST first1, SECOND second1, THIRD third1, FOURTH fourth1,
            FIRST first2, SECOND second2, THIRD third2, FOURTH fourth2,
            FIRST first3, SECOND second3, THIRD third3, FOURTH fourth3
    ) {
        return List.of(
                immutable(first0, second0, third0, fourth0),
                immutable(first1, second1, third1, fourth1),
                immutable(first2, second2, third2, fourth2),
                immutable(first3, second3, third3, fourth3)
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> of(
            FIRST first0, SECOND second0, THIRD third0, FOURTH fourth0,
            FIRST first1, SECOND second1, THIRD third1, FOURTH fourth1,
            FIRST first2, SECOND second2, THIRD third2, FOURTH fourth2,
            FIRST first3, SECOND second3, THIRD third3, FOURTH fourth3,
            FIRST first4, SECOND second4, THIRD third4, FOURTH fourth4
    ) {
        return List.of(
                immutable(first0, second0, third0, fourth0),
                immutable(first1, second1, third1, fourth1),
                immutable(first2, second2, third2, fourth2),
                immutable(first3, second3, third3, fourth3),
                immutable(first4, second4, third4, fourth4)
        );
    }


    Quadruple<?, ?, ?, ?> EMPTY = Quadruple.of(null, null, null, null);
    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH> Quadruple<FIRST, SECOND, THIRD, FOURTH> empty() {
        return (Quadruple<FIRST, SECOND, THIRD, FOURTH>) EMPTY;
    }

    static <FIRST, SECOND, THIRD, FOURTH, R> Function<Quadruple<FIRST, SECOND, THIRD, FOURTH>, R> toFunction(TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> function) {
        return quadruple -> function.apply(quadruple.first(), quadruple.second(), quadruple.third(), quadruple.fourth());
    }
    static <FIRST, SECOND, THIRD, FOURTH, R> TetraFunction<FIRST, SECOND, THIRD, FOURTH, R> toTetraFunction(Function<Quadruple<FIRST, SECOND, THIRD, FOURTH>, R> function) {
        return (first, second, third, fourth) -> function.apply(Quadruple.of(first, second, third, fourth));
    }
    static <FIRST, SECOND, THIRD, FOURTH, R> Function<R, Quadruple<FIRST, SECOND, THIRD, FOURTH>> toFunction(Function<R, FIRST> toFirst, Function<R, SECOND> toSecond, Function<R, THIRD> toThird, Function<R, FOURTH> toFourth) {
        return r -> Quadruple.of(toFirst.apply(r), toSecond.apply(r), toThird.apply(r), toFourth.apply(r));
    }

    static <FIRST, SECOND, THIRD, FOURTH> Predicate<Quadruple<FIRST, SECOND, THIRD, FOURTH>> toPredicate(TetraPredicate<FIRST, SECOND, THIRD, FOURTH> predicate) {
        return quadruple -> predicate.test(quadruple.first(), quadruple.second(), quadruple.third(), quadruple.fourth());
    }
    static <FIRST, SECOND, THIRD, FOURTH> TetraPredicate<FIRST, SECOND, THIRD, FOURTH> toTetraPredicate(Predicate<Quadruple<FIRST, SECOND, THIRD, FOURTH>> predicate) {
        return (first, second, third, fourth) -> predicate.test(Quadruple.of(first, second, third, fourth));
    }

    static <FIRST, SECOND, THIRD, FOURTH> Consumer<Quadruple<FIRST, SECOND, THIRD, FOURTH>> toConsumer(TetraConsumer<FIRST, SECOND, THIRD, FOURTH> consumer) {
        return quadruple -> consumer.accept(quadruple.first(), quadruple.second(), quadruple.third(), quadruple.fourth());
    }
    static <FIRST, SECOND, THIRD, FOURTH> TetraConsumer<FIRST, SECOND, THIRD, FOURTH> toTetraConsumer(Consumer<Quadruple<FIRST, SECOND, THIRD, FOURTH>> consumer) {
        return (first, second, third, fourth) -> consumer.accept(Quadruple.of(first, second, third, fourth));
    }


    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> zip(List<FIRST> firstList, List<SECOND> secondList, List<THIRD> thirdList, List<FOURTH> fourthList) {
        ArrayList<Quadruple<FIRST, SECOND, THIRD, FOURTH>> zippedList = new ArrayList<>(Math.min(Math.min(Math.min(firstList.size(), secondList.size()), thirdList.size()), fourthList.size()));
        Iterator<FIRST> firstIterator = firstList.iterator();
        Iterator<SECOND> secondIterator = secondList.iterator();
        Iterator<THIRD> thirdIterator = thirdList.iterator();
        Iterator<FOURTH> fourthIterator = fourthList.iterator();
        while (firstIterator.hasNext() && secondIterator.hasNext() && thirdIterator.hasNext() && fourthIterator.hasNext()) {
            zippedList.add(of(firstIterator.next(), secondIterator.next(), thirdIterator.next(), fourthIterator.next()));
        }
        return List.copyOf(zippedList);
    }

    static <FIRST, SECOND, THIRD, FOURTH> Quadruple<List<FIRST>, List<SECOND>, List<THIRD>, List<FOURTH>> unzip(List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> zippedList) {
        List<FIRST> firstList = new ArrayList<>(zippedList.size());
        List<SECOND> secondList = new ArrayList<>(zippedList.size());
        List<THIRD> thirdList = new ArrayList<>(zippedList.size());
        List<FOURTH> fourthList = new ArrayList<>(zippedList.size());
        for (Quadruple<FIRST, SECOND, THIRD, FOURTH> zipped : zippedList) {
            firstList.add(zipped.first());
            secondList.add(zipped.second());
            thirdList.add(zipped.third());
            fourthList.add(zipped.fourth());
        }
        return of(firstList, secondList, thirdList, fourthList);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> of(Object... objects) {
        ArrayList<Quadruple<FIRST, SECOND, THIRD, FOURTH>> list = new ArrayList<>(objects.length / 4);
        int length = objects.length / 4 * 4;
        for (int index = 0; index < length; index += 4)
            list.add(of((FIRST) objects[index], (SECOND) objects[index + 1], (THIRD) objects[index + 2], (FOURTH) objects[index + 3]));
        return List.copyOf(list);
    }
    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> of(List<?> objects) {
        ArrayList<Quadruple<FIRST, SECOND, THIRD, FOURTH>> list = new ArrayList<>(objects.size() / 4);
        int length = objects.size() / 4 * 4;
        for (int index = 0; index < length; index += 4)
            list.add(of((FIRST) objects.get(index), (SECOND) objects.get(index + 1), (THIRD) objects.get(index + 2), (FOURTH) objects.get(index + 3)));
        return List.copyOf(list);
    }

    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> joinFirst(SECOND second, THIRD third, FOURTH fourth, List<FIRST> firsts) {
        return firsts.stream().map(first -> of(first, second, third, fourth)).toList();
    }
    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> joinSecond(FIRST first, THIRD third, FOURTH fourth, List<SECOND> seconds) {
        return seconds.stream().map(second -> of(first, second, third, fourth)).toList();
    }
    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> joinThird(FIRST first, SECOND second, FOURTH fourth, List<THIRD> thirds) {
        return thirds.stream().map(third -> of(first, second, third, fourth)).toList();
    }
    static <FIRST, SECOND, THIRD, FOURTH> List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> joinFourth(FIRST first, SECOND second, THIRD third, List<FOURTH> fourths) {
        return fourths.stream().map(fourth -> of(first, second, third, fourth)).toList();
    }

    static <FIRST, SECOND, THIRD, FOURTH> Map<FIRST, List<Triple<SECOND, THIRD, FOURTH>>> groupByFirst(List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> quadruples) {
        return quadruples.stream().collect(
                Collectors.groupingBy(
                        Quadruple::first,
                        Collectors.mapping(
                                quadruple -> Triple.of(quadruple.second(), quadruple.third(), quadruple.fourth()),
                                Collectors.toList()
                        )
                )
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH> Map<SECOND, List<Triple<FIRST, THIRD, FOURTH>>> groupBySecond(List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> quadruples) {
        return quadruples.stream().collect(
                Collectors.groupingBy(
                        Quadruple::second,
                        Collectors.mapping(
                                quadruple -> Triple.of(quadruple.first(), quadruple.third(), quadruple.fourth()),
                                Collectors.toList()
                        )
                )
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH> Map<THIRD, List<Triple<FIRST, SECOND, FOURTH>>> groupByThird(List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> quadruples) {
        return quadruples.stream().collect(
                Collectors.groupingBy(
                        Quadruple::third,
                        Collectors.mapping(
                                quadruple -> Triple.of(quadruple.first(), quadruple.second(), quadruple.fourth()),
                                Collectors.toList()
                        )
                )
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH> Map<FOURTH, List<Triple<FIRST, SECOND, THIRD>>> groupByFourth(List<Quadruple<FIRST, SECOND, THIRD, FOURTH>> quadruples) {
        return quadruples.stream().collect(
                Collectors.groupingBy(
                        Quadruple::fourth,
                        Collectors.mapping(
                                quadruple -> Triple.of(quadruple.first(), quadruple.second(), quadruple.third()),
                                Collectors.toList()
                        )
                )
        );
    }


    final class Immutable<FIRST, SECOND, THIRD, FOURTH> implements Quadruple<FIRST, SECOND, THIRD, FOURTH> {
        final FIRST first;
        final SECOND second;
        final THIRD third;
        final FOURTH fourth;

        Immutable(FIRST first, SECOND second, THIRD third, FOURTH fourth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
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
        public Triple<SECOND, THIRD, FOURTH> removeFirst() {
            return Triple.immutable(second, third, fourth);
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
        public Triple<FIRST, THIRD, FOURTH> removeSecond() {
            return Triple.immutable(first, third, fourth);
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
        public Triple<FIRST, SECOND, FOURTH> removeThird() {
            return Triple.immutable(first, second, fourth);
        }
        @Override
        public FOURTH fourth() {
            return fourth;
        }
        @Override
        public void fourth(FOURTH newValue) {
            throw new UnsupportedOperationException();
        }
        @Override
        public Triple<FIRST, SECOND, THIRD> removeFourth() {
            return Triple.immutable(first, second, third);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Quadruple<?, ?, ?, ?>)) return false;
            return equals((Quadruple<?, ?, ?, ?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first, second, third, fourth);
        }

        transient String toStringCache = null;
        @Override
        public String toString() {
            if (toStringCache == null)
                toStringCache = new StringJoiner(",", "(", ")").add(String.valueOf(first)).add(String.valueOf(second)).add(String.valueOf(third)).add(String.valueOf(fourth)).toString();
            return toStringCache;
        }

        @Override
        public <FIFTH> Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> append(FIFTH fifth) {
            return Quintuple.immutable(first, second, third, fourth, fifth);
        }
    }

    final class Mutable<FIRST, SECOND, THIRD, FOURTH> implements Quadruple<FIRST, SECOND, THIRD, FOURTH> {
        FIRST first;
        SECOND second;
        THIRD third;
        FOURTH fourth;

        Mutable(FIRST first, SECOND second, THIRD third, FOURTH fourth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
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
        public Triple<SECOND, THIRD, FOURTH> removeFirst() {
            return Triple.mutable(second, third, fourth);
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
        public Triple<FIRST, THIRD, FOURTH> removeSecond() {
            return Triple.mutable(first, third, fourth);
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
        public Triple<FIRST, SECOND, FOURTH> removeThird() {
            return Triple.mutable(first, second, fourth);
        }
        @Override
        public FOURTH fourth() {
            return fourth;
        }
        @Override
        public void fourth(FOURTH newValue) {
            this.fourth = newValue;
        }
        @Override
        public Triple<FIRST, SECOND, THIRD> removeFourth() {
            return Triple.mutable(first, second, third);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Quadruple<?, ?, ?, ?>)) return false;
            return equals((Quadruple<?, ?, ?, ?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first, second, third, fourth);
        }

        @Override
        public String toString() {
            return new StringJoiner(",", "(", ")").add(String.valueOf(first)).add(String.valueOf(second)).add(String.valueOf(third)).add(String.valueOf(fourth)).toString();
        }

        @Override
        public <FIFTH> Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> append(FIFTH fifth) {
            return Quintuple.mutable(first, second, third, fourth, fifth);
        }
    }
}