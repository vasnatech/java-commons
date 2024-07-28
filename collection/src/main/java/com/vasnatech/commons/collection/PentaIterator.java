package com.vasnatech.commons.collection;

import com.vasnatech.commons.function.PentaConsumer;
import com.vasnatech.commons.function.PentaPredicate;
import com.vasnatech.commons.type.tuple.Quintuple;

import java.util.Iterator;
import java.util.function.Consumer;

public interface PentaIterator<FIRST, SECOND, THIRD, FOURTH, FIFTH> extends Iterator<Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> {

    PentaPredicate<Boolean, Boolean, Boolean, Boolean, Boolean> OR_PREDICATE = (first, second, third, fourth, fifth) -> first || second || third || fourth || fifth;
    PentaPredicate<Boolean, Boolean, Boolean, Boolean, Boolean> AND_PREDICATE = (first, second, third, fourth, fifth) -> first && second && third && fourth && fifth;


    @Override
    default boolean hasNext() {
        return oneHasNext();
    }

    boolean firstHasNext();

    boolean secondHasNext();

    boolean thirdHasNext();

    boolean fourthHasNext();

    boolean fifthHasNext();

    default boolean allHasNext() {
        return firstHasNext() && secondHasNext() && thirdHasNext() && fourthHasNext() && fifthHasNext();
    }

    default boolean oneHasNext() {
        return firstHasNext() || secondHasNext() || thirdHasNext() || fourthHasNext() || fifthHasNext();
    }

    default boolean noneHasNext() {
        return !firstHasNext() && !secondHasNext() && !thirdHasNext() && !fourthHasNext() && !fifthHasNext();
    }

    FIRST firstNext();

    SECOND secondNext();

    THIRD thirdNext();

    FOURTH fourthNext();

    FIFTH fifthNext();

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

    default void forEachRemainingFifth(Consumer<? super FIFTH> action) {
        while (fifthHasNext())
            action.accept(fifthNext());
    }

    @Override
    default void forEachRemaining(Consumer<? super Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> action) {
        forEachOneRemaining(action);
    }

    default void forEachOneRemaining(Consumer<? super Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> action) {
        while (oneHasNext())
            action.accept(next());
    }

    default void forEachAllRemaining(Consumer<? super Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> action) {
        while (allHasNext())
            action.accept(next());
    }

    default void forEachRemaining(PentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> action) {
        forEachOneRemaining(action);
    }

    default void forEachOneRemaining(PentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> action) {
        while (oneHasNext()) {
            Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> next = next();
            action.accept(next.first(), next.second(), next.third(), next.fourth(), next.fifth());
        }
    }

    default void forEachAllRemaining(PentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> action) {
        while (allHasNext()) {
            Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> next = next();
            action.accept(next.first(), next.second(), next.third(), next.fourth(), next.fifth());
        }
    }

    default void forEachRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction, Consumer<? super FIFTH> fifthAction) {
        forEachOneRemaining(firstAction, secondAction, thirdAction, fourthAction, fifthAction);
    }

    default void forEachOneRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction, Consumer<? super FIFTH> fifthAction) {
        while (oneHasNext()) {
            Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> next = next();
            firstAction.accept(next.first());
            secondAction.accept(next.second());
            thirdAction.accept(next.third());
            fourthAction.accept(next.fourth());
            fifthAction.accept(next.fifth());
        }
    }

    default void forEachAllRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction, Consumer<? super FIFTH> fifthAction) {
        while (allHasNext()) {
            Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> next = next();
            firstAction.accept(next.first());
            secondAction.accept(next.second());
            thirdAction.accept(next.third());
            fourthAction.accept(next.fourth());
            fifthAction.accept(next.fifth());
        }
    }


    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaIterator<FIRST, SECOND, THIRD, FOURTH, FIFTH> of(Iterable<FIRST> firstIterable, Iterable<SECOND> secondIterable, Iterable<THIRD> thirdIterable, Iterable<FOURTH> fourthIterable, Iterable<FIFTH> fifthIterable) {
        return of(firstIterable.iterator(), secondIterable.iterator(), thirdIterable.iterator(), fourthIterable.iterator(), fifthIterable.iterator());
    }

    static <FIRST, SECOND, THIRD, FOURTH, FIFTH> PentaIterator<FIRST, SECOND, THIRD, FOURTH, FIFTH> of(Iterator<FIRST> firstIterator, Iterator<SECOND> secondIterator, Iterator<THIRD> thirdIterator, Iterator<FOURTH> fourthIterator, Iterator<FIFTH> fifthIterator) {
        return new Default<>(firstIterator, secondIterator, thirdIterator, fourthIterator, fifthIterator);
    }



    class Default<FIRST, SECOND, THIRD, FOURTH, FIFTH> implements PentaIterator<FIRST, SECOND, THIRD, FOURTH, FIFTH> {

        final Iterator<FIRST> firstIterator;
        final Iterator<SECOND> secondIterator;
        final Iterator<THIRD> thirdIterator;
        final Iterator<FOURTH> fourthIterator;
        final Iterator<FIFTH> fifthIterator;

        Default(Iterator<FIRST> firstIterator, Iterator<SECOND> secondIterator, Iterator<THIRD> thirdIterator, Iterator<FOURTH> fourthIterator, Iterator<FIFTH> fifthIterator) {
            this.firstIterator = firstIterator;
            this.secondIterator = secondIterator;
            this.thirdIterator = thirdIterator;
            this.fourthIterator = fourthIterator;
            this.fifthIterator = fifthIterator;
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
        public boolean fifthHasNext() {
            return fifthIterator.hasNext();
        }

        @Override
        public FIRST firstNext() {
            if (secondIterator.hasNext())
                secondIterator.next();
            if (thirdIterator.hasNext())
                thirdIterator.next();
            if (fourthIterator.hasNext())
                fourthIterator.next();
            if (fifthIterator.hasNext())
                fifthIterator.next();
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
            if (fifthIterator.hasNext())
                fifthIterator.next();
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
            if (fifthIterator.hasNext())
                fifthIterator.next();
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
            if (fifthIterator.hasNext())
                fifthIterator.next();
            return fourthIterator.next();
        }

        @Override
        public FIFTH fifthNext() {
            if (firstIterator.hasNext())
                firstIterator.next();
            if (secondIterator.hasNext())
                secondIterator.next();
            if (thirdIterator.hasNext())
                thirdIterator.next();
            if (fourthIterator.hasNext())
                fourthIterator.next();
            return fifthIterator.next();
        }

        @Override
        public Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH> next() {
            return Quintuple.of(
                    firstIterator.hasNext() ? firstIterator.next() : null,
                    secondIterator.hasNext() ? secondIterator.next() : null,
                    thirdIterator.hasNext() ? thirdIterator.next() : null,
                    fourthIterator.hasNext() ? fourthIterator.next() : null,
                    fifthIterator.hasNext() ? fifthIterator.next() : null
            );
        }

        @Override
        public void forEachOneRemaining(Consumer<? super Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> action) {
            forEachRemaining(action, OR_PREDICATE);
        }

        @Override
        public void forEachAllRemaining(Consumer<? super Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> action) {
            forEachRemaining(action, AND_PREDICATE);
        }

        public void forEachRemaining(Consumer<? super Quintuple<FIRST, SECOND, THIRD, FOURTH, FIFTH>> action, PentaPredicate<Boolean, Boolean, Boolean, Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            boolean hasThird =  thirdIterator.hasNext();
            boolean hasFourth =  fourthIterator.hasNext();
            boolean hasFifth =  fifthIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond, hasThird, hasFourth, hasFifth)) {
                action.accept(
                        Quintuple.of(
                                hasFirst ? firstIterator.next() : null,
                                hasSecond ? secondIterator.next() : null,
                                hasThird ? thirdIterator.next() : null,
                                hasFourth ? fourthIterator.next() : null,
                                hasFifth ? fifthIterator.next() : null
                        )
                );
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
                hasThird =  thirdIterator.hasNext();
                hasFourth =  fourthIterator.hasNext();
                hasFifth =  fifthIterator.hasNext();
            }
        }

        @Override
        public void forEachOneRemaining(PentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> action) {
            forEachRemaining(action, OR_PREDICATE);
        }

        @Override
        public void forEachAllRemaining(PentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> action) {
            forEachRemaining(action, AND_PREDICATE);
        }

        void forEachRemaining(PentaConsumer<? super FIRST, ? super SECOND, ? super THIRD, ? super FOURTH, ? super FIFTH> action, PentaPredicate<Boolean, Boolean, Boolean, Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            boolean hasThird =  thirdIterator.hasNext();
            boolean hasFourth =  fourthIterator.hasNext();
            boolean hasFifth =  fifthIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond, hasThird, hasFourth, hasFifth)) {
                action.accept(
                        hasFirst ? firstIterator.next() : null,
                        hasSecond ? secondIterator.next() : null,
                        hasThird ? thirdIterator.next() : null,
                        hasFourth ? fourthIterator.next() : null,
                        hasFifth ? fifthIterator.next() : null
                );
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
                hasThird =  thirdIterator.hasNext();
                hasFourth =  fourthIterator.hasNext();
                hasFifth =  fifthIterator.hasNext();
            }
        }

        @Override
        public void forEachOneRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction, Consumer<? super FIFTH> fifthAction) {
            forEachAllRemaining(firstAction, secondAction, thirdAction, fourthAction, fifthAction, OR_PREDICATE);
        }

        @Override
        public void forEachAllRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction, Consumer<? super FIFTH> fifthAction) {
            forEachAllRemaining(firstAction, secondAction, thirdAction, fourthAction, fifthAction, AND_PREDICATE);
        }

        void forEachAllRemaining(Consumer<? super FIRST> firstAction, Consumer<? super SECOND> secondAction, Consumer<? super THIRD> thirdAction, Consumer<? super FOURTH> fourthAction, Consumer<? super FIFTH> fifthAction, PentaPredicate<Boolean, Boolean, Boolean, Boolean, Boolean> predicate) {
            boolean hasFirst =  firstIterator.hasNext();
            boolean hasSecond =  secondIterator.hasNext();
            boolean hasThird =  thirdIterator.hasNext();
            boolean hasFourth =  fourthIterator.hasNext();
            boolean hasFifth =  fifthIterator.hasNext();
            while (predicate.test(hasFirst, hasSecond, hasThird, hasFourth, hasFifth)) {
                firstAction.accept(hasFirst ? firstIterator.next() : null);
                secondAction.accept(hasSecond ? secondIterator.next() : null);
                thirdAction.accept(hasThird ? thirdIterator.next() : null);
                fourthAction.accept(hasFourth ? fourthIterator.next() : null);
                fifthAction.accept(hasFifth ? fifthIterator.next() : null);
                hasFirst =  firstIterator.hasNext();
                hasSecond =  secondIterator.hasNext();
                hasThird =  thirdIterator.hasNext();
                hasFourth =  fourthIterator.hasNext();
                hasFifth =  fifthIterator.hasNext();
            }
        }
    }
}
