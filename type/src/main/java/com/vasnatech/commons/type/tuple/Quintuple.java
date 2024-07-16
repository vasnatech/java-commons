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

public interface Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> extends Tuple {

    FIRST first();
    void first(FIRST newValue);
    Quadruple<SECOND, THIRD, FOURTH, FIFTH> removeFirst();

    SECOND second();
    void second(SECOND newValue);
    Quadruple<FIRST, THIRD, FOURTH, FIFTH> removeSecond();

    THIRD third();
    void third(THIRD newValue);
    Quadruple<FIRST, SECOND, FOURTH, FIFTH> removeThird();

    FOURTH fourth();
    void fourth(FOURTH newValue);
    Quadruple<FIRST, SECOND, THIRD, FIFTH> removeFourth();

    FIFTH fifth();
    void fifth(FIFTH fifth);
    Quadruple<FIRST, SECOND, THIRD, FOURTH> removeFifth();

    @Override
    @SuppressWarnings("unchecked")
    default FIRST head() {
        return first();
    }
    @Override
    @SuppressWarnings("unchecked")
    default FIFTH tail() {
        return fifth();
    }

    default Quadruple<SECOND, THIRD, FOURTH, FIFTH> removeHead() {
        return removeFirst();
    }
    default Quadruple<FIRST, SECOND, THIRD, FOURTH> removeTail() {
        return removeFifth();
    }

    @Override
    default int length() {
        return 5;
    }

    @Override
    @SuppressWarnings("unchecked")
    default Object get(int index) {
        return switch (index) {
            case 0 -> first();
            case 1 -> second();
            case 2 -> third();
            case 3 -> fourth();
            case 4 -> fifth();
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
        } else if (index == 4) {
            fifth((FIFTH) value);
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
            case 4 -> removeFifth();
            default -> throw new IndexOutOfBoundsException(index);
        };
    }

    @Override
    default Object[] toArray() {
        return new Object[] {first(), second(), third(), fourth(), fifth()};
    }

    @Override
    default List<?> toList() {
        return List.of(first(), second(), third(), fourth(), fifth());
    }

    @Override
    <SIXTH> Tuple append(SIXTH sixth);

    default <R> R apply(PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> function) {
        return function.apply(first(), second(), third(), fourth(), fifth());
    }
    default boolean test(PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> predicate) {
        return predicate.test(first(), second(), third(), fourth(), fifth());
    }
    default void accept(PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> consumer) {
        consumer.accept(first(), second(), third(), fourth(), fifth());
    }
    default void accept(Consumer<FIRST> firstConsumer, Consumer<SECOND> secondConsumer, Consumer<THIRD> thirdConsumer, Consumer<FOURTH> fourthConsumer, Consumer<FIFTH> fifthConsumer) {
        firstConsumer.accept(first());
        secondConsumer.accept(second());
        thirdConsumer.accept(third());
        fourthConsumer.accept(fourth());
        fifthConsumer.accept(fifth());
    }


    default boolean equals(Quintuple<?, ?, ?, ?, ?> that) {
        if (this == that) return true;
        if (that == null) return false;
        return Objects.equals(this.first(), that.first()) &&
               Objects.equals(this.second(), that.second()) &&
               Objects.equals(this.third(), that.third()) &&
               Objects.equals(this.fourth(), that.fourth()) &&
               Objects.equals(this.fifth(), that.fifth());
    }


    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Immutable<FIRST, SECOND, THIRD, FOURTH, FIFTH> immutable(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) {
        return new Immutable<>(first, second, third, fourth, fifth);
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Mutable<FIRST, SECOND, THIRD, FOURTH, FIFTH> mutable(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) {
        return new Mutable<>(first, second, third, fourth, fifth);
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> of(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) {
        return immutable(first, second, third, fourth, fifth);
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> of(
            FIRST first0, SECOND second0, THIRD third0, FOURTH fourth0, FIFTH fifth0,
            FIRST first1, SECOND second1, THIRD third1, FOURTH fourth1, FIFTH fifth1
    ) {
        return List.of(
                immutable(first0, second0, third0, fourth0, fifth0),
                immutable(first1, second1, third1, fourth1, fifth1)
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> of(
            FIRST first0, SECOND second0, THIRD third0, FOURTH fourth0, FIFTH fifth0,
            FIRST first1, SECOND second1, THIRD third1, FOURTH fourth1, FIFTH fifth1,
            FIRST first2, SECOND second2, THIRD third2, FOURTH fourth2, FIFTH fifth2
    ) {
        return List.of(
                immutable(first0, second0, third0, fourth0, fifth0),
                immutable(first1, second1, third1, fourth1, fifth1),
                immutable(first2, second2, third2, fourth2, fifth2)
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> of(
            FIRST first0, SECOND second0, THIRD third0, FOURTH fourth0, FIFTH fifth0,
            FIRST first1, SECOND second1, THIRD third1, FOURTH fourth1, FIFTH fifth1,
            FIRST first2, SECOND second2, THIRD third2, FOURTH fourth2, FIFTH fifth2,
            FIRST first3, SECOND second3, THIRD third3, FOURTH fourth3, FIFTH fifth3
    ) {
        return List.of(
                immutable(first0, second0, third0, fourth0, fifth0),
                immutable(first1, second1, third1, fourth1, fifth1),
                immutable(first2, second2, third2, fourth2, fifth2),
                immutable(first3, second3, third3, fourth3, fifth3)
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> of(
            FIRST first0, SECOND second0, THIRD third0, FOURTH fourth0, FIFTH fifth0,
            FIRST first1, SECOND second1, THIRD third1, FOURTH fourth1, FIFTH fifth1,
            FIRST first2, SECOND second2, THIRD third2, FOURTH fourth2, FIFTH fifth2,
            FIRST first3, SECOND second3, THIRD third3, FOURTH fourth3, FIFTH fifth3,
            FIRST first4, SECOND second4, THIRD third4, FOURTH fourth4, FIFTH fifth4
    ) {
        return List.of(
                immutable(first0, second0, third0, fourth0, fifth0),
                immutable(first1, second1, third1, fourth1, fifth1),
                immutable(first2, second2, third2, fourth2, fifth2),
                immutable(first3, second3, third3, fourth3, fifth3),
                immutable(first4, second4, third4, fourth4, fifth4)
        );
    }


    Quintuple<?, ?, ?, ?, ?> EMPTY = Quintuple.of(null, null, null, null, null);
    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> empty() {
        return (Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>) EMPTY;
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R> Function<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>, R> toFunction(PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> function) {
        return quintuple -> function.apply(quintuple.first(), quintuple.second(), quintuple.third(), quintuple.fourth(), quintuple.fifth());
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R> PentaFunction<FIRST, SECOND, THIRD, FOURTH, FIFTH, R> toPentaFunction(Function<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>, R> function) {
        return (first, second, third, fourth, fifth) -> function.apply(Quintuple.of(first, second, third, fourth, fifth));
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH, R> Function<R, Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> toFunction(Function<R, FIRST> toFirst, Function<R, SECOND> toSecond, Function<R, THIRD> toThird, Function<R, FOURTH> toFourth, Function<R, FIFTH> toFifth) {
        return r -> Quintuple.of(toFirst.apply(r), toSecond.apply(r), toThird.apply(r), toFourth.apply(r), toFifth.apply(r));
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Predicate<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> toPredicate(PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> predicate) {
        return quintuple -> predicate.test(quintuple.first(), quintuple.second(), quintuple.third(), quintuple.fourth(), quintuple.fifth());
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaPredicate<FIRST, SECOND, THIRD, FOURTH, FIFTH> toTetraPredicate(Predicate<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> predicate) {
        return (first, second, third, fourth, fifth) -> predicate.test(Quintuple.of(first, second, third, fourth, fifth));
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Consumer<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> toConsumer(PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> consumer) {
        return quintuple -> consumer.accept(quintuple.first(), quintuple.second(), quintuple.third(), quintuple.fourth(), quintuple.fifth());
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaConsumer<FIRST, SECOND, THIRD, FOURTH, FIFTH> toPentaConsumer(Consumer<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> consumer) {
        return (first, second, third, fourth, fifth) -> consumer.accept(Quintuple.of(first, second, third, fourth, fifth));
    }


    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> zip(List<FIRST> firstList, List<SECOND> secondList, List<THIRD> thirdList, List<FOURTH> fourthList, List<FIFTH> fifthList) {
        ArrayList<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> zippedList = new ArrayList<>(Math.min(Math.min(Math.min(Math.min(firstList.size(), secondList.size()), thirdList.size()), fourthList.size()), firstList.size()));
        Iterator<FIRST> firstIterator = firstList.iterator();
        Iterator<SECOND> secondIterator = secondList.iterator();
        Iterator<THIRD> thirdIterator = thirdList.iterator();
        Iterator<FOURTH> fourthIterator = fourthList.iterator();
        Iterator<FIFTH> fifthIterator = fifthList.iterator();
        while (firstIterator.hasNext() && secondIterator.hasNext() && thirdIterator.hasNext() && fourthIterator.hasNext() && fifthIterator.hasNext()) {
            zippedList.add(of(firstIterator.next(), secondIterator.next(), thirdIterator.next(), fourthIterator.next(), fifthIterator.next()));
        }
        return List.copyOf(zippedList);
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Quintuple<List<FIRST>, List<SECOND>, List<THIRD>, List<FOURTH>, List<FIFTH>> unzip(List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> zippedList) {
        List<FIRST> firstList = new ArrayList<>(zippedList.size());
        List<SECOND> secondList = new ArrayList<>(zippedList.size());
        List<THIRD> thirdList = new ArrayList<>(zippedList.size());
        List<FOURTH> fourthList = new ArrayList<>(zippedList.size());
        List<FIFTH> fifthList = new ArrayList<>(zippedList.size());
        for (Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> zipped : zippedList) {
            firstList.add(zipped.first());
            secondList.add(zipped.second());
            thirdList.add(zipped.third());
            fourthList.add(zipped.fourth());
            fifthList.add(zipped.fifth());
        }
        return of(firstList, secondList, thirdList, fourthList, fifthList);
    }

    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> of(Object... objects) {
        ArrayList<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> list = new ArrayList<>(objects.length / 5);
        int length = objects.length / 5 * 5;
        for (int index = 0; index < length; index += 5)
            list.add(of((FIRST) objects[index], (SECOND) objects[index + 1], (THIRD) objects[index + 2], (FOURTH) objects[index + 3], (FIFTH) objects[index + 4]));
        return List.copyOf(list);
    }
    @SuppressWarnings("unchecked")
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> of(List<?> objects) {
        ArrayList<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> list = new ArrayList<>(objects.size() / 5);
        int length = objects.size() / 5 * 5;
        for (int index = 0; index < length; index += 5)
            list.add(of((FIRST) objects.get(index), (SECOND) objects.get(index + 1), (THIRD) objects.get(index + 2), (FOURTH) objects.get(index + 3), (FIFTH) objects.get(index + 4)));
        return List.copyOf(list);
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> joinFirst(SECOND second, THIRD third, FOURTH fourth, FIFTH fifth, List<FIRST> firsts) {
        return firsts.stream().map(first -> of(first, second, third, fourth, fifth)).toList();
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> joinSecond(FIRST first, THIRD third, FOURTH fourth, FIFTH fifth, List<SECOND> seconds) {
        return seconds.stream().map(second -> of(first, second, third, fourth, fifth)).toList();
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> joinThird(FIRST first, SECOND second, FOURTH fourth, FIFTH fifth, List<THIRD> thirds) {
        return thirds.stream().map(third -> of(first, second, third, fourth, fifth)).toList();
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> joinFourth(FIRST first, SECOND second, THIRD third, FIFTH fifth, List<FOURTH> fourths) {
        return fourths.stream().map(fourth -> of(first, second, third, fourth, fifth)).toList();
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> joinFifth(FIRST first, SECOND second, THIRD third, FOURTH fourth, List<FIFTH> fifths) {
        return fifths.stream().map(fifth -> of(first, second, third, fourth, fifth)).toList();
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Map<FIRST, List<Quadruple<SECOND, THIRD, FOURTH, FIFTH>>> groupByFirst(List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> quintuples) {
        return quintuples.stream().collect(
                Collectors.groupingBy(
                        Quintuple::first,
                        Collectors.mapping(
                                quintuple -> Quadruple.of(quintuple.second(), quintuple.third(), quintuple.fourth(), quintuple.fifth()),
                                Collectors.toList()
                        )
                )
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Map<SECOND, List<Quadruple<FIRST, THIRD, FOURTH, FIFTH>>> groupBySecond(List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> quintuples) {
        return quintuples.stream().collect(
                Collectors.groupingBy(
                        Quintuple::second,
                        Collectors.mapping(
                                quintuple -> Quadruple.of(quintuple.first(), quintuple.third(), quintuple.fourth(), quintuple.fifth()),
                                Collectors.toList()
                        )
                )
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Map<THIRD, List<Quadruple<FIRST, SECOND, FOURTH, FIFTH>>> groupByThird(List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> quintuples) {
        return quintuples.stream().collect(
                Collectors.groupingBy(
                        Quintuple::third,
                        Collectors.mapping(
                                quintuple -> Quadruple.of(quintuple.first(), quintuple.second(), quintuple.fourth(), quintuple.fifth()),
                                Collectors.toList()
                        )
                )
        );
    }
    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Map<FOURTH, List<Quadruple<FIRST, SECOND, THIRD, FIFTH>>> groupByFourth(List<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> quintuples) {
        return quintuples.stream().collect(
                Collectors.groupingBy(
                        Quintuple::fourth,
                        Collectors.mapping(
                                quintuple -> Quadruple.of(quintuple.first(), quintuple.second(), quintuple.third(), quintuple.fifth()),
                                Collectors.toList()
                        )
                )
        );
    }


    final class Immutable<FIRST, SECOND, THIRD, FOURTH, FIFTH> implements Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> {
        final FIRST first;
        final SECOND second;
        final THIRD third;
        final FOURTH fourth;
        final FIFTH fifth;

        Immutable(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
            this.fifth = fifth;
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
        public Quadruple<SECOND, THIRD, FOURTH, FIFTH> removeFirst() {
            return Quadruple.immutable(second, third, fourth, fifth);
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
        public Quadruple<FIRST, THIRD, FOURTH, FIFTH> removeSecond() {
            return Quadruple.immutable(first, third, fourth, fifth);
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
        public Quadruple<FIRST, SECOND, FOURTH, FIFTH> removeThird() {
            return Quadruple.immutable(first, second, fourth, fifth);
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
        public Quadruple<FIRST, SECOND, THIRD, FIFTH> removeFourth() {
            return Quadruple.immutable(first, second, third, fifth);
        }
        @Override
        public FIFTH fifth() {
            return fifth;
        }
        @Override
        public void fifth(FIFTH fifth) {
            throw new UnsupportedOperationException();
        }
        @Override
        public Quadruple<FIRST, SECOND, THIRD, FOURTH> removeFifth() {
            return Quadruple.immutable(first, second, third, fourth);
        }

        @Override
        public <SIXTH> Tuple append(SIXTH sixth) {
            return TupleN.immutable(first, second, third, fourth, fifth, sixth);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Quintuple<?, ?, ?, ?, ?>)) return false;
            return equals((Quintuple<?, ?, ?, ?, ?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first, second, third, fourth, fifth);
        }

        transient String toStringCache = null;
        @Override
        public String toString() {
            if (toStringCache == null)
                toStringCache = new StringJoiner(",", "(", ")").add(String.valueOf(first)).add(String.valueOf(second)).add(String.valueOf(third)).add(String.valueOf(fourth)).add(String.valueOf(fifth)).toString();
            return toStringCache;
        }
    }

    final class Mutable<FIRST, SECOND, THIRD, FOURTH, FIFTH> implements Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> {
        FIRST first;
        SECOND second;
        THIRD third;
        FOURTH fourth;
        FIFTH fifth;

        Mutable(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
            this.fifth = fifth;
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
        public Quadruple<SECOND, THIRD, FOURTH, FIFTH> removeFirst() {
            return Quadruple.mutable(second, third, fourth, fifth);
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
        public Quadruple<FIRST, THIRD, FOURTH, FIFTH> removeSecond() {
            return Quadruple.mutable(first, third, fourth, fifth);
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
        public Quadruple<FIRST, SECOND, FOURTH, FIFTH> removeThird() {
            return Quadruple.mutable(first, second, fourth, fifth);
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
        public Quadruple<FIRST, SECOND, THIRD, FIFTH> removeFourth() {
            return Quadruple.mutable(first, second, third, fifth);
        }
        @Override
        public FIFTH fifth() {
            return fifth;
        }
        @Override
        public void fifth(FIFTH fifth) {
            this.fifth = fifth;
        }
        @Override
        public Quadruple<FIRST, SECOND, THIRD, FOURTH> removeFifth() {
            return Quadruple.mutable(first, second, third, fourth);
        }

        @Override
        public <SIXTH> Tuple append(SIXTH sixth) {
            return TupleN.mutable(first, second, third, fourth, fifth, sixth);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Quintuple<?, ?, ?, ?, ?>)) return false;
            return equals((Quintuple<?, ?, ?, ?, ?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first, second, third, fourth, fifth);
        }

        @Override
        public String toString() {
            return new StringJoiner(",", "(", ")").add(String.valueOf(first)).add(String.valueOf(second)).add(String.valueOf(third)).add(String.valueOf(fourth)).add(String.valueOf(fifth)).toString();
        }
    }
}