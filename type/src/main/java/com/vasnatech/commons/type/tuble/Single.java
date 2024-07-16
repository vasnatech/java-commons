package com.vasnatech.commons.type.tuble;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Single<FIRST> extends Tuple {

    FIRST first();
    void first(FIRST newValue);
    Tuple0 removeFirst();

    @Override
    default int length() {
        return 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    default Object get(int index) {
        if (index == 0)
            return first();
        throw new IndexOutOfBoundsException(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    default void set(int index, Object value) {
        if (index == 0) {
            first((FIRST) value);
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    default Tuple remove(int index) {
        if (index == 0) {
            return removeFirst();
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    default Object[] toArray() {
        return new Object[] {first()};
    }

    @Override
    default List<?> toList() {
        return List.of(first());
    }

    <SECOND> Pair<FIRST, SECOND> append(SECOND second);


    default <R> R apply(Function<FIRST, R> function) {
        return function.apply(first());
    }
    default boolean test(Predicate<FIRST> predicate) {
        return predicate.test(first());
    }
    default void accept(Consumer<FIRST> consumer) {
        consumer.accept(first());
    }


    default boolean equals(Single<?> that) {
        if (this == that) return true;
        if (that == null) return false;
        return Objects.equals(this.first(), that.first());
    }


    static <FIRST> Single<FIRST> of(FIRST first) {
        return immutable(first);
    }
    static <FIRST> List<Single<FIRST>> of(FIRST f0, FIRST f1) {
        return List.of(immutable(f0), immutable(f1));
    }
    static <FIRST> List<Single<FIRST>> of(FIRST f0, FIRST f1, FIRST f2) {
        return List.of(immutable(f0), immutable(f1), immutable(f2));
    }
    static <FIRST> List<Single<FIRST>> of(FIRST f0, FIRST f1, FIRST f2, FIRST f3) {
        return List.of(immutable(f0), immutable(f1), immutable(f2), immutable(f3));
    }
    static <FIRST> List<Single<FIRST>> of(FIRST f0, FIRST f1, FIRST f2, FIRST f3, FIRST f4) {
        return List.of(immutable(f0), immutable(f1), immutable(f2), immutable(f3), immutable(f4));
    }

    static <FIRST> Immutable<FIRST> immutable(FIRST first) {
        return new Immutable<>(first);
    }
    static <FIRST> Mutable<FIRST> mutable(FIRST first) {
        return new Mutable<>(first);
    }

    Single<?> EMPTY = Single.of((Object) null);
    @SuppressWarnings("unchecked")
    static <FIRST> Single<FIRST> empty() {
        return (Single<FIRST>) EMPTY;
    }

    static <FIRST, R> Function<Single<FIRST>, R> toFunction(Function<FIRST, R> function) {
        return single -> function.apply(single.first());
    }

    static <FIRST> Predicate<Single<FIRST>> toPredicate(Predicate<FIRST> predicate) {
        return single -> predicate.test(single.first());
    }

    static <FIRST> Consumer<Single<FIRST>> toConsumer(Consumer<FIRST> consumer) {
        return single -> consumer.accept(single.first());
    }

    @SuppressWarnings("unchecked")
    static <FIRST> List<Single<FIRST>> of(Object... objects) {
        return Stream.of(objects).map(it -> (FIRST)it).map(Single::of).toList();
    }
    @SuppressWarnings("unchecked")
    static <FIRST> List<Single<FIRST>> of(List<?> objects) {
        return objects.stream().map(it -> (FIRST)it).map(Single::of).toList();
    }


    final class Immutable<FIRST> implements Single<FIRST> {
        final FIRST first;

        Immutable(FIRST first) {
            this.first = first;
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
        public Tuple0 removeFirst() {
            return Tuple0.immutable();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Single<?>)) return false;
            return equals((Single<?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first);
        }

        transient String toStringCache = null;
        @Override
        public String toString() {
            if (toStringCache == null)
                toStringCache = new StringJoiner(",", "(", ")").add(String.valueOf(first)).toString();
            return toStringCache;
        }

        @Override
        public <SECOND> Pair<FIRST, SECOND> append(SECOND second) {
            return Pair.immutable(first, second);
        }
    }

    final class Mutable<FIRST> implements Single<FIRST> {
        FIRST first;

        Mutable(FIRST first) {
            this.first = first;
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
        public Tuple0 removeFirst() {
            return Tuple0.mutable();
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Single<?>)) return false;
            return equals((Single<?>) o);
        }
        @Override
        public int hashCode() {
            return Objects.hash(first);
        }

        @Override
        public String toString() {
            return new StringJoiner(",", "(", ")").add(String.valueOf(first)).toString();
        }


        @Override
        public <SECOND> Pair<FIRST, SECOND> append(SECOND second) {
            return Pair.mutable(first, second);
        }
    }
}
