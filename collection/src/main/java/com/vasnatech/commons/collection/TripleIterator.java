package com.vasnatech.commons.collection;

import com.vasnatech.commons.function.TriConsumer;
import com.vasnatech.commons.function.TriPredicate;
import com.vasnatech.commons.type.tuple.Triple;

import java.util.Iterator;
import java.util.function.Consumer;

public interface TripleIterator<FIRST, SECOND, THIRD> extends Iterator<Triple<FIRST, SECOND, THIRD>> {

    TriPredicate<Boolean, Boolean, Boolean> OR_PREDICATE = (first, second, third) -> first || second || third;
    TriPredicate<Boolean, Boolean, Boolean> AND_PREDICATE = (first, second, third) -> first && second && third;


    @Override
    default boolean hasNext() {
        return oneHasNext();
    }

    boolean firstHasNext();

    boolean secondHasNext();

    boolean thirdHasNext();

    default boolean allHasNext() {
        return firstHasNext() && secondHasNext() && thirdHasNext();
    }

    default boolean oneHasNext() {
        return firstHasNext() || secondHasNext() || thirdHasNext();
    }

    default boolean noneHasNext() {
        return !firstHasNext() && !secondHasNext() && !thirdHasNext();
    }

    FIRST firstNext();

    SECOND secondNext();

    THIRD thirdNext();

    default void forEachRemainingFirst(Consumer<? super FIRST> action) {
        while (firstHasNext())
            action.accept(firstNext());
    }

    default void forEachRemainingSecond(Consumer<? super SECOND> action) {
        while (secondHasNext())
            action.accept(secondNext());
    }

    default void forEachRemainingThird(Consumer<? super THIRD> action) {
        while (thirdHasNext())
            action.accept(thirdNext());
    }

    @Override
    default void forEachRemaining(Consumer<? super Triple<FIRST, SECOND, THIRD>> action) {
        forEachOneRemaining(action);
    }

    default void forEachOneRemaining(Consumer<? super Triple<FIRST, SECOND, THIRD>> action) {
        while (oneHasNext())
            action.accept(next());
    }

    default void forEachAllRemaining(Consumer<? super Triple<FIRST, SECOND, THIRD>> action) {
        while (allHasNext())
            action.accept(next());
    }

    default void forEachRemaining(TriConsumer<? super FIRST, ? super SECOND, ? super THIRD> action) {
        forEachOneRemaining(action);
    }

    default void forEachOneRemaining(TriConsumer<? super FIRST, ? super SECOND, ? super THIRD> action) {
        while (oneHasNext()) {
            Triple<FIRST, SECOND, THIRD> next = next();
            action.accept(next.first(), next.second(), next.third());
        }
    }

    default void forEachAllRemaining(TriConsumer<? super FIRST, ? super SECOND, ? super THIRD> action) {
        while (allHasNext()) {
            Triple<FIRST, SECOND, THIRD> next = next();
            action.accept(next.first(), next.second(), next.third());
        }
    }

    default void forEachRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction) {
        forEachOneRemaining(firstAction, secondAction, thirdAction);
    }

    default void forEachOneRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction) {
        while (oneHasNext()) {
            Triple<FIRST, SECOND, THIRD> next = next();
            firstAction.accept(next.first());
            secondAction.accept(next.second());
            thirdAction.accept(next.third());
        }
    }

    default void forEachAllRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction) {
        while (allHasNext()) {
            Triple<FIRST, SECOND, THIRD> next = next();
            firstAction.accept(next.first());
            secondAction.accept(next.second());
            thirdAction.accept(next.third());
        }
    }


    static <FIRST, SECOND, THIRD> TripleIterator<FIRST, SECOND, THIRD> of(Iterable<FIRST> firstIterable, Iterable<SECOND> secondIterable, Iterable<THIRD> thirdIterable) {
        return of(firstIterable.iterator(), secondIterable.iterator(), thirdIterable.iterator());
    }

    static <FIRST, SECOND, THIRD> TripleIterator<FIRST, SECOND, THIRD> of(Iterator<FIRST> firstIterator, Iterator<SECOND> secondIterator, Iterator<THIRD> thirdIterator) {
        return new Default<>(firstIterator, secondIterator, thirdIterator);
    }



    class Default<FIRST, SECOND, THIRD> implements TripleIterator<FIRST, SECOND, THIRD> {

        final Iterator<FIRST> firstIterator;
        final Iterator<SECOND> secondIterator;
        final Iterator<THIRD> thirdIterator;

        Default(Iterator<FIRST> firstIterator, Iterator<SECOND> secondIterator, Iterator<THIRD> thirdIterator) {
            this.firstIterator = firstIterator;
            this.secondIterator = secondIterator;
            this.thirdIterator = thirdIterator;
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
        public boolean thirdHasNext() {
            return thirdIterator.hasNext();
        }

        @Override
        public FIRST firstNext() {
            if (secondIterator.hasNext())
                secondIterator.next();
            if (thirdIterator.hasNext())
                thirdIterator.next();
            return firstIterator.next();
        }

        @Override
        public SECOND secondNext() {
            if (firstIterator.hasNext())
                firstIterator.next();
            if (thirdIterator.hasNext())
                thirdIterator.next();
            return secondIterator.next();
        }

        @Override
        public THIRD thirdNext() {
            if (firstIterator.hasNext())
                firstIterator.next();
            if (secondIterator.hasNext())
                secondIterator.next();
            return thirdIterator.next();
        }

        @Override
        public Triple<FIRST, SECOND, THIRD> next() {
            return Triple.of(
                    firstIterator.hasNext() ? firstIterator.next() : null,
                    secondIterator.hasNext() ? secondIterator.next() : null,
                    thirdIterator.hasNext() ? thirdIterator.next() : null
            );
        }

        @Override
        public void forEachOneRemaining(Consumer<? super Triple<FIRST, SECOND, THIRD>> action) {
            forEachRemaining(action, OR_PREDICATE);
        }

        @Override
        public void forEachAllRemaining(Consumer<? super Triple<FIRST, SECOND, THIRD>> action) {
            forEachRemaining(action, AND_PREDICATE);
        }

        public void forEachRemaining(Consumer<? super Triple<FIRST, SECOND, THIRD>> action, TriPredicate<Boolean, Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            boolean hasThird =  thirdIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond, hasThird)) {
                action.accept(
                        Triple.of(
                                hasFirst ? firstIterator.next() : null,
                                hasSecond ? secondIterator.next() : null,
                                hasThird ? thirdIterator.next() : null
                        )
                );
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
                hasThird =  thirdIterator.hasNext();
            }
        }

        @Override
        public void forEachOneRemaining(TriConsumer<? super FIRST, ? super SECOND, ? super THIRD> action) {
            forEachRemaining(action, OR_PREDICATE);
        }

        @Override
        public void forEachAllRemaining(TriConsumer<? super FIRST, ? super SECOND, ? super THIRD> action) {
            forEachRemaining(action, AND_PREDICATE);
        }

        void forEachRemaining(TriConsumer<? super FIRST, ? super SECOND, ? super THIRD> action, TriPredicate<Boolean, Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            boolean hasThird =  thirdIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond, hasThird)) {
                action.accept(
                        hasFirst ? firstIterator.next() : null,
                        hasSecond ? secondIterator.next() : null,
                        hasThird ? thirdIterator.next() : null
                );
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
                hasThird =  thirdIterator.hasNext();
            }
        }

        @Override
        public void forEachOneRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction) {
            forEachRemaining(firstAction, secondAction, thirdAction, OR_PREDICATE);
        }

        @Override
        public void forEachAllRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction) {
            forEachRemaining(firstAction, secondAction, thirdAction, AND_PREDICATE);
        }

        void forEachRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, TriPredicate<Boolean, Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            boolean hasThird =  thirdIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond, hasThird)) {
                firstAction.accept(hasFirst ? firstIterator.next() : null);
                secondAction.accept(hasSecond ? secondIterator.next() : null);
                thirdAction.accept(hasThird ? thirdIterator.next() : null);
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
                hasThird =  thirdIterator.hasNext();
            }
        }
    }
}
