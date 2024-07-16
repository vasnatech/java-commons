package com.vasnatech.commons.type.tuble;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class TupleN implements Tuple {

    final Object[] elements;

    TupleN(Object... elements) {
        this.elements = elements;
    }

    @Override
    public int length() {
        return elements.length;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V get(int index) {
        return (V) elements[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof TupleN that) {
            return Arrays.equals(this.elements, that.elements);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    Object[] remove(Object[] elements, int index) {
        if (index < 0 || index >= elements.length) {
            throw new IndexOutOfBoundsException(index);
        }
        Object[] newElements = new Object[elements.length - 1];
        System.arraycopy(elements, 0, newElements, 0, index);
        System.arraycopy(elements, index + 1, newElements, index, elements.length - index - 1);
        return newElements;
    }


    static TupleN of(Object... elements) {
        return immutable(elements);
    }
    static TupleN immutable(Object... elements) {
        return new Immutable(elements);
    }
    static TupleN mutable(Object... elements) {
        return new Mutable(elements);
    }


    static final class Immutable extends TupleN {

        Immutable(Object... elements) {
            super(elements);
        }

        @Override
        public <V> void set(int index, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Tuple remove(int index) {
            Object[] newElements = remove(elements, index);
            if (newElements.length == 5) {
                return Quintuple.immutable(newElements[0], newElements[1], newElements[2], newElements[3], newElements[4]);
            }
            return TupleN.immutable(newElements);
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOf(elements, elements.length);
        }

        @Override
        public List<?> toList() {
            return List.of(elements);
        }

        @Override
        public <A> Tuple append(A newValue) {
            Object[] newElements = Arrays.copyOf(elements, elements.length + 1);
            newElements[elements.length] = newValue;
            return new Immutable(newElements);
        }

        transient String toStringCache = null;
        @Override
        public String toString() {
            if (toStringCache == null)
                toStringCache = Stream.of(elements).map(String::valueOf).collect(Collectors.joining(", ", "(", ")"));
            return toStringCache;
        }
    }


    static final class Mutable extends TupleN {

        Mutable(Object... elements) {
            super(elements);
        }

        @Override
        public <V> void set(int index, V value) {
            elements[index] = value;
        }

        @Override
        public Tuple remove(int index) {
            Object[] newElements = remove(elements, index);
            if (newElements.length == 5) {
                return Quintuple.mutable(newElements[0], newElements[1], newElements[2], newElements[3], newElements[4]);
            }
            return TupleN.mutable(newElements);
        }

        @Override
        public Object[] toArray() {
            return elements;
        }

        @Override
        public List<?> toList() {
            return List.of(elements);
        }

        @Override
        public <A> Tuple append(A newValue) {
            Object[] newElements = Arrays.copyOf(elements, elements.length + 1);
            newElements[elements.length] = newValue;
            return new Mutable(newElements);
        }

        @Override
        public String toString() {
            return Stream.of(elements).map(String::valueOf).collect(Collectors.joining(", ", "(", ")"));
        }
    }
}
