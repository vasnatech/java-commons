package com.vasnatech.commons.collection;

import com.vasnatech.commons.function.TetraConsumer;
import com.vasnatech.commons.function.TetraPredicate;
import com.vasnatech.commons.type.tuple.Quadruple;

import java.util.Iterator;
import java.util.function.Consumer;

public interface TetraIterator<FIRST, SECOND, THIRD, FOURTH> extends Iterator<Quadruple<FIRST, SECOND, THIRD, FOURTH>> {

    TetraPredicate<Boolean, Boolean, Boolean, Boolean> OR_PREDICATE = (first, second, third, fourth) -> first || second || third || fourth;
    TetraPredicate<Boolean, Boolean, Boolean, Boolean> AND_PREDICATE = (first, second, third, fourth) -> first && second && third && fourth;


    @Override
    default boolean hasNext() {
        return oneHasNext();
    }

    boolean firstHasNext();

    boolean secondHasNext();

    boolean thirdHasNext();

    boolean fourthHasNext();

    default boolean allHasNext() {
        return firstHasNext() && secondHasNext() && thirdHasNext() && fourthHasNext();
    }

    default boolean oneHasNext() {
        return firstHasNext() || secondHasNext() || thirdHasNext() || fourthHasNext();
    }

    default boolean noneHasNext() {
        return !firstHasNext() && !secondHasNext() && !thirdHasNext() && !fourthHasNext();
    }

    FIRST firstNext();

    SECOND secondNext();

    THIRD thirdNext();

    FOURTH fourthNext();

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

    default void forEachRemainingFourth(Consumer<? super FOURTH> action) {
        while (fourthHasNext())
            action.accept(fourthNext());
    }

    @Override
    default void forEachRemaining(Consumer<? super Quadruple<FIRST, SECOND, THIRD, FOURTH>> action) {
        forEachOneRemaining(action);
    }

    default void forEachOneRemaining(Consumer<? super Quadruple<FIRST, SECOND, THIRD, FOURTH>> action) {
        while (oneHasNext())
            action.accept(next());
    }

    default void forEachAllRemaining(Consumer<? super Quadruple<FIRST, SECOND, THIRD, FOURTH>> action) {
        while (allHasNext())
            action.accept(next());
    }

    default void forEachRemaining(TetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> action) {
        forEachOneRemaining(action);
    }

    default void forEachOneRemaining(TetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> action) {
        while (oneHasNext()) {
            Quadruple<FIRST, SECOND, THIRD, FOURTH> next = next();
            action.accept(next.first(), next.second(), next.third(), next.fourth());
        }
    }

    default void forEachAllRemaining(TetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> action) {
        while (allHasNext()) {
            Quadruple<FIRST, SECOND, THIRD, FOURTH> next = next();
            action.accept(next.first(), next.second(), next.third(), next.fourth());
        }
    }

    default void forEachRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction) {
        forEachOneRemaining(firstAction, secondAction, thirdAction, fourthAction);
    }

    default void forEachOneRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction) {
        while (oneHasNext()) {
            Quadruple<FIRST, SECOND, THIRD, FOURTH> next = next();
            firstAction.accept(next.first());
            secondAction.accept(next.second());
            thirdAction.accept(next.third());
            fourthAction.accept(next.fourth());
        }
    }

    default void forEachAllRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction) {
        while (allHasNext()) {
            Quadruple<FIRST, SECOND, THIRD, FOURTH> next = next();
            firstAction.accept(next.first());
            secondAction.accept(next.second());
            thirdAction.accept(next.third());
            fourthAction.accept(next.fourth());
        }
    }


    static <FIRST, SECOND, THIRD, FOURTH> TetraIterator<FIRST, SECOND, THIRD, FOURTH> of(Iterable<FIRST> firstIterable, Iterable<SECOND> secondIterable, Iterable<THIRD> thirdIterable, Iterable<FOURTH> fourthIterable) {
        return of(firstIterable.iterator(), secondIterable.iterator(), thirdIterable.iterator(), fourthIterable.iterator());
    }

    static <FIRST, SECOND, THIRD, FOURTH> TetraIterator<FIRST, SECOND, THIRD, FOURTH> of(Iterator<FIRST> firstIterator, Iterator<SECOND> secondIterator, Iterator<THIRD> thirdIterator, Iterator<FOURTH> fourthIterator) {
        return new Default<>(firstIterator, secondIterator, thirdIterator, fourthIterator);
    }



    class Default<FIRST, SECOND, THIRD, FOURTH> implements TetraIterator<FIRST, SECOND, THIRD, FOURTH> {

        final Iterator<FIRST> firstIterator;
        final Iterator<SECOND> secondIterator;
        final Iterator<THIRD> thirdIterator;
        final Iterator<FOURTH> fourthIterator;

        Default(Iterator<FIRST> firstIterator, Iterator<SECOND> secondIterator, Iterator<THIRD> thirdIterator, Iterator<FOURTH> fourthIterator) {
            this.firstIterator = firstIterator;
            this.secondIterator = secondIterator;
            this.thirdIterator = thirdIterator;
            this.fourthIterator = fourthIterator;
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
        public boolean fourthHasNext() {
            return fourthIterator.hasNext();
        }

        @Override
        public FIRST firstNext() {
            if (secondIterator.hasNext())
                secondIterator.next();
            if (thirdIterator.hasNext())
                thirdIterator.next();
            if (fourthIterator.hasNext())
                fourthIterator.next();
            return firstIterator.next();
        }

        @Override
        public SECOND secondNext() {
            if (firstIterator.hasNext())
                firstIterator.next();
            if (thirdIterator.hasNext())
                thirdIterator.next();
            if (fourthIterator.hasNext())
                fourthIterator.next();
            return secondIterator.next();
        }

        @Override
        public THIRD thirdNext() {
            if (firstIterator.hasNext())
                firstIterator.next();
            if (secondIterator.hasNext())
                secondIterator.next();
            if (fourthIterator.hasNext())
                fourthIterator.next();
            return thirdIterator.next();
        }

        @Override
        public FOURTH fourthNext() {
            if (firstIterator.hasNext())
                firstIterator.next();
            if (secondIterator.hasNext())
                secondIterator.next();
            if (thirdIterator.hasNext())
                thirdIterator.next();
            return fourthIterator.next();
        }

        @Override
        public Quadruple<FIRST, SECOND, THIRD, FOURTH> next() {
            return Quadruple.of(
                    firstIterator.hasNext() ? firstIterator.next() : null,
                    secondIterator.hasNext() ? secondIterator.next() : null,
                    thirdIterator.hasNext() ? thirdIterator.next() : null,
                    fourthIterator.hasNext() ? fourthIterator.next() : null
            );
        }

        @Override
        public void forEachOneRemaining(Consumer<? super Quadruple<FIRST, SECOND, THIRD, FOURTH>> action) {
            forEachRemaining(action, OR_PREDICATE);
        }

        @Override
        public void forEachAllRemaining(Consumer<? super Quadruple<FIRST, SECOND, THIRD, FOURTH>> action) {
            forEachRemaining(action, AND_PREDICATE);
        }

        public void forEachRemaining(Consumer<? super Quadruple<FIRST, SECOND, THIRD, FOURTH>> action, TetraPredicate<Boolean, Boolean, Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            boolean hasThird =  thirdIterator.hasNext();
            boolean hasFourth =  fourthIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond, hasThird, hasFourth)) {
                action.accept(
                        Quadruple.of(
                                hasFirst ? firstIterator.next() : null,
                                hasSecond ? secondIterator.next() : null,
                                hasThird ? thirdIterator.next() : null,
                                hasFourth ? fourthIterator.next() : null
                        )
                );
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
                hasThird =  thirdIterator.hasNext();
                hasFourth =  fourthIterator.hasNext();
            }
        }

        @Override
        public void forEachOneRemaining(TetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> action) {
            forEachRemaining(action, OR_PREDICATE);
        }

        @Override
        public void forEachAllRemaining(TetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> action) {
            forEachRemaining(action, AND_PREDICATE);
        }

        void forEachRemaining(TetraConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH> action, TetraPredicate<Boolean, Boolean, Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            boolean hasThird =  thirdIterator.hasNext();
            boolean hasFourth =  fourthIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond, hasThird, hasFourth)) {
                action.accept(
                        hasFirst ? firstIterator.next() : null,
                        hasSecond ? secondIterator.next() : null,
                        hasThird ? thirdIterator.next() : null,
                        hasFourth ? fourthIterator.next() : null
                );
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
                hasThird =  thirdIterator.hasNext();
                hasFourth =  fourthIterator.hasNext();
            }
        }

        @Override
        public void forEachOneRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction) {
            forEachRemaining(firstAction, secondAction, thirdAction, fourthAction, OR_PREDICATE);
        }

        @Override
        public void forEachAllRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction) {
            forEachRemaining(firstAction, secondAction, thirdAction, fourthAction, AND_PREDICATE);
        }

        void forEachRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction, TetraPredicate<Boolean, Boolean, Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            boolean hasThird =  thirdIterator.hasNext();
            boolean hasFourth =  fourthIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond, hasThird, hasFourth)) {
                firstAction.accept(hasFirst ? firstIterator.next() : null);
                secondAction.accept(hasSecond ? secondIterator.next() : null);
                thirdAction.accept(hasThird ? thirdIterator.next() : null);
                fourthAction.accept(hasFourth ? fourthIterator.next() : null);
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
                hasThird =  thirdIterator.hasNext();
                hasFourth =  fourthIterator.hasNext();
            }
        }
    }
}
