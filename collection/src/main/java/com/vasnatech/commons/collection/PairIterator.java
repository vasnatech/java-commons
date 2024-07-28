package com.vasnatech.commons.collection;

import com.vasnatech.commons.type.tuple.Pair;

import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public interface PairIterator<FIRST, SECOND> extends Iterator<Pair<FIRST, SECOND>> {

    @Override
    default boolean hasNext() {
        return oneHasNext();
    }

    boolean firstHasNext();

    boolean secondHasNext();

    default boolean bothHasNext() {
        return allHasNext();
    }

    default boolean allHasNext() {
        return firstHasNext() && secondHasNext();
    }

    default boolean oneHasNext() {
        return firstHasNext() || secondHasNext();
    }

    default boolean noneHasNext() {
        return !firstHasNext() && !secondHasNext();
    }

    FIRST firstNext();

    SECOND secondNext();

    default void forEachRemainingFirst(Consumer<? super FIRST> action) {
        while (firstHasNext())
            action.accept(firstNext());
    }

    default void forEachRemainingSecond(Consumer<? super SECOND> action) {
        while (secondHasNext())
            action.accept(secondNext());
    }

    @Override
    default void forEachRemaining(Consumer<? super Pair<FIRST, SECOND>> action) {
        forEachOneRemaining(action);
    }

    default void forEachOneRemaining(Consumer<? super Pair<FIRST, SECOND>> action) {
        while (oneHasNext())
            action.accept(next());
    }

    default void forEachAllRemaining(Consumer<? super Pair<FIRST, SECOND>> action) {
        while (allHasNext())
            action.accept(next());
    }

    default void forEachBothRemaining(Consumer<? super Pair<FIRST, SECOND>> action) {
        forEachAllRemaining(action);
    }

    default void forEachRemaining(BiConsumer<? super FIRST, ? super SECOND> action) {
        forEachOneRemaining(action);
    }

    default void forEachOneRemaining(BiConsumer<? super FIRST, ? super SECOND> action) {
        while (oneHasNext()) {
            Pair<FIRST, SECOND> next = next();
            action.accept(next.first(), next.second());
        }
    }

    default void forEachAllRemaining(BiConsumer<? super FIRST, ? super SECOND> action) {
        while (allHasNext()) {
            Pair<FIRST, SECOND> next = next();
            action.accept(next.first(), next.second());
        }
    }

    default void forEachBothRemaining(BiConsumer<? super FIRST, ? super SECOND> action) {
        forEachAllRemaining(action);
    }

    default void forEachRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction) {
        forEachOneRemaining(firstAction, secondAction);
    }

    default void forEachOneRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction) {
        while (oneHasNext()) {
            Pair<FIRST, SECOND> next = next();
            firstAction.accept(next.first());
            secondAction.accept(next.second());
        }
    }

    default void forEachAllRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction) {
        while (allHasNext()) {
            Pair<FIRST, SECOND> next = next();
            firstAction.accept(next.first());
            secondAction.accept(next.second());
        }
    }

    default void forEachBothRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction) {
        forEachAllRemaining(firstAction, secondAction);
    }


    static <FIRST, SECOND> PairIterator<FIRST, SECOND> of(Iterable<FIRST> firstIterable, Iterable<SECOND> secondIterable) {
        return of(firstIterable.iterator(), secondIterable.iterator());
    }

    static <FIRST, SECOND> PairIterator<FIRST, SECOND> of(Iterator<FIRST> firstIterator, Iterator<SECOND> secondIterator) {
        return new Default<>(firstIterator, secondIterator);
    }



    class Default<FIRST, SECOND> implements PairIterator<FIRST, SECOND> {

        final Iterator<FIRST> firstIterator;
        final Iterator<SECOND> secondIterator;

        Default(Iterator<FIRST> firstIterator, Iterator<SECOND> secondIterator) {
            this.firstIterator = firstIterator;
            this.secondIterator = secondIterator;
        }

        @Override
        public boolean firstHasNext() {
            return firstIterator.hasNext();
        }

        @Override
        public boolean secondHasNext() {
            return secondIterator.hasNext();
        }

        @Override
        public FIRST firstNext() {
            if (secondIterator.hasNext())
                secondIterator.next();
            return firstIterator.next();
        }

        @Override
        public SECOND secondNext() {
            if (firstIterator.hasNext())
                firstIterator.next();
            return secondIterator.next();
        }

        @Override
        public Pair<FIRST, SECOND> next() {
            return Pair.of(
                    firstIterator.hasNext() ? firstIterator.next() : null,
                    secondIterator.hasNext() ? secondIterator.next() : null
            );
        }

        @Override
        public void forEachOneRemaining(Consumer<? super Pair<FIRST, SECOND>> action) {
            forEachRemaining(action, Boolean::logicalOr);
        }

        @Override
        public void forEachAllRemaining(Consumer<? super Pair<FIRST, SECOND>> action) {
            forEachRemaining(action, Boolean::logicalAnd);
        }

        public void forEachRemaining(Consumer<? super Pair<FIRST, SECOND>> action, BiPredicate<Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond)) {
                action.accept(
                        Pair.of(
                                hasFirst ? firstIterator.next() : null,
                                hasSecond ? secondIterator.next() : null
                        )
                );
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
            }
        }

        @Override
        public void forEachOneRemaining(BiConsumer<? super FIRST, ? super SECOND> action) {
            forEachRemaining(action, Boolean::logicalOr);
        }

        @Override
        public void forEachAllRemaining(BiConsumer<? super FIRST, ? super SECOND> action) {
            forEachRemaining(action, Boolean::logicalAnd);
        }

        void forEachRemaining(BiConsumer<? super FIRST, ? super SECOND> action, BiPredicate<Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond)) {
                action.accept(
                        hasFirst ? firstIterator.next() : null,
                        hasSecond ? secondIterator.next() : null
                );
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
            }
        }

        @Override
        public void forEachOneRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction) {
            forEachRemaining(firstAction, secondAction, Boolean::logicalOr);
        }

        @Override
        public void forEachAllRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction) {
            forEachRemaining(firstAction, secondAction, Boolean::logicalAnd);
        }

        void forEachRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, BiPredicate<Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond)) {
                firstAction.accept(hasFirst ? firstIterator.next() : null);
                secondAction.accept(hasSecond ? secondIterator.next() : null);
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
            }
        }
    }
}
