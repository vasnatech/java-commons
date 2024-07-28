package com.vasnatech.commons.type.tuple;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface Tuple extends Comparable<Tuple>, Iterable<Object> {

    int length();

    <V> V get(int index);
    <V> void set(int index, V value);
    Tuple remove(int index);

    Object head();
    Tuple removeHead();

    Object tail();
    Tuple removeTail();

    default Object[] toArray() {
        return IntStream.range(0, length()).mapToObj(this::get).toArray(Object[]::new);
    }

    default List<?> toList() {
        return IntStream.range(0, length()).mapToObj(this::get).toList();
    }

    @Override
    default Iterator<Object> iterator() {
        return new TupleIterator(this);
    }

    <A> Tuple append(A newValue);

    default boolean equals(Tuple that) {
        if (this == that) return true;
        if (that == null) return false;
        if (this.length() != this.length()) return false;
        return IntStream.range(0, length()).allMatch(index -> Objects.equals(this.get(index), that.get(index)));
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    default int compareTo(Tuple that) {
        int length = Math.min(this.length(), that.length());
        for (int index = 0; index < length; ++index) {
            Object thisElement = this.get(index);
            Object thatElement = that.get(index);
            if (thisElement == thatElement) {
                continue;
            }
            if (thisElement == null) {
                return -1;
            }
            if (thatElement == null) {
                return 1;
            }

            Class<?> thisElementClass = thisElement.getClass();
            Class<?> thatElementClass = thatElement.getClass();
            if (thisElementClass.isAssignableFrom(thatElementClass)) {
                if (Comparable.class.isAssignableFrom(thisElementClass)) {
                    int result = ((Comparable)thisElement).compareTo(thatElement);
                    if (result == 0)
                        continue;
                    return result;
                }
            } else if (thatElementClass.isAssignableFrom(thisElementClass)) {
                if (Comparable.class.isAssignableFrom(thatElementClass)) {
                    int result = ((Comparable)thatElement).compareTo(thisElement);
                    if (result == 0)
                        continue;
                    return -1 * result;
                }
            }

            return thisElementClass.getName().compareTo(thatElementClass.getName());
        }
        return Integer.compare(this.length(), that.length());
    }

    static <FIRST> Tuple of(FIRST first) {
        return Single.of(first);
    }

    static <FIRST, SECOND> Tuple of(FIRST first, SECOND second) {
        return Pair.of(first, second);
    }

    static <FIRST, SECOND, THIRD> Tuple of(FIRST first, SECOND second, THIRD third) {
        return Triple.of(first, second, third);
    }

    static <FIRST, SECOND, THIRD, FOURTH> Tuple of(FIRST first, SECOND second, THIRD third, FOURTH fourth) {
        return Quadruple.of(first, second, third, fourth);
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Tuple of(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) {
        return Quintuple.of(first, second, third, fourth, fifth);
    }

    static Tuple of(Object first, Object second, Object third, Object fourth, Object fifth, Object... elements) {
        return TupleN.of(
                Stream.of(
                        Stream.of(first, second, third, fourth, fifth),
                        Stream.of(elements)
                )
                .flatMap(Function.identity())
                .toArray()
        );
    }


    static <FIRST> Tuple immutable(FIRST first) {
        return Single.immutable(first);
    }

    static <FIRST, SECOND> Tuple immutable(FIRST first, SECOND second) {
        return Pair.immutable(first, second);
    }

    static <FIRST, SECOND, THIRD> Tuple immutable(FIRST first, SECOND second, THIRD third) {
        return Triple.immutable(first, second, third);
    }

    static <FIRST, SECOND, THIRD, FOURTH> Tuple immutable(FIRST first, SECOND second, THIRD third, FOURTH fourth) {
        return Quadruple.immutable(first, second, third, fourth);
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Tuple immutable(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) {
        return Quintuple.immutable(first, second, third, fourth, fifth);
    }

    static Tuple immutable(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth, Object... elements) {
        return TupleN.immutable(
                Stream.of(
                        Stream.of(first, second, third, fourth, fifth, sixth),
                        Stream.of(elements)
                )
                .flatMap(Function.identity())
                .toArray()
        );
    }


    static <FIRST> Tuple mutable(FIRST first) {
        return Single.mutable(first);
    }

    static <FIRST, SECOND> Tuple mutable(FIRST first, SECOND second) {
        return Pair.mutable(first, second);
    }

    static <FIRST, SECOND, THIRD> Tuple mutable(FIRST first, SECOND second, THIRD third) {
        return Triple.mutable(first, second, third);
    }

    static <FIRST, SECOND, THIRD, FOURTH> Tuple mutable(FIRST first, SECOND second, THIRD third, FOURTH fourth) {
        return Quadruple.mutable(first, second, third, fourth);
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> Tuple mutable(FIRST first, SECOND second, THIRD third, FOURTH fourth, FIFTH fifth) {
        return Quintuple.mutable(first, second, third, fourth, fifth);
    }

    static Tuple mutable(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth, Object... elements) {
        return TupleN.mutable(
                Stream.of(
                        Stream.of(first, second, third, fourth, fifth, sixth),
                        Stream.of(elements)
                )
                .flatMap(Function.identity())
                .toArray()
        );
    }


    class TupleIterator implements Iterator<Object> {

        final Tuple tuple;
        int index = -1;

        public TupleIterator(Tuple tuple) {
            this.tuple = tuple;
        }

        @Override
        public boolean hasNext() {
            return index + 1 < tuple.length();
        }

        @Override
        public Object next() {
            return tuple.get(++index);
        }

        @Override
        public void remove() {
            tuple.remove(index);
        }
    }
}
