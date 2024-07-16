package com.vasnatech.commons.type.tuble;

import java.util.List;

public interface Tuple0 extends Tuple {

    @Override
    default int length() {
        return 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    default Object get(int index) {
        throw new IndexOutOfBoundsException(index);
    }

    @Override
    default void set(int index, Object value) {
        throw new IndexOutOfBoundsException(index);
    }

    @Override
    default Tuple remove(int index) {
        throw new IndexOutOfBoundsException(index);
    }

    @Override
    default Object[] toArray() {
        return new Object[0];
    }

    @Override
    default List<?> toList() {
        return List.of();
    }

    <FIRST> Single<FIRST> append(FIRST first);


    default boolean equals(Tuple0 that) {
        if (this == that) return true;
        return that != null;
    }


    static Tuple0 of() {
        return immutable();
    }
    static Tuple0 immutable() {
        return Immutable.of();
    }
    static Tuple0 mutable() {
        return Mutable.of();
    }


    final class Immutable implements Tuple0 {

        Immutable() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Tuple0)) return false;
            return equals((Tuple0) o);
        }
        @Override
        public int hashCode() {
            return 1;
        }

        transient String toStringCache = "()";
        @Override
        public String toString() {
            return toStringCache;
        }

        @Override
        public <FIRST> Single<FIRST> append(FIRST first) {
            return Single.immutable(first);
        }

        static Immutable INSTANCE = new Immutable();
        public static Immutable of() {
            return INSTANCE;
        }
    }

    final class Mutable implements Tuple0 {

        Mutable() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Tuple0)) return false;
            return equals((Tuple0) o);
        }
        @Override
        public int hashCode() {
            return 1;
        }

        transient String toStringCache = "()";
        @Override
        public String toString() {
            return toStringCache;
        }

        @Override
        public <FIRST> Single<FIRST> append(FIRST first) {
            return Single.mutable(first);
        }

        static Mutable INSTANCE = new Mutable();
        public static Mutable of() {
            return INSTANCE;
        }
    }
}
