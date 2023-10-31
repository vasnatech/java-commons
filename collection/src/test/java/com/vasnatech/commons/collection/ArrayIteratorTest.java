package com.vasnatech.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayIteratorTest {

    @Test
    void hasNext_next() {
        ArrayIterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9);
        ArrayList<Integer> actual = new ArrayList<>(10);
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        assertThat(actual).isEqualTo(List.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9));
    }

    @Test
    void hasPrevious_previous() {
        ArrayIterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9);
        ArrayList<Integer> actual = new ArrayList<>(10);
        while (iterator.hasPrevious()) {
            actual.add(iterator.previous());
        }
        assertThat(actual).isEqualTo(List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
    }

    @Test
    void next_previous() {
        ArrayIterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9);

        Integer next1 = iterator.next();
        Integer next2 = iterator.next();
        Integer prev1 = iterator.previous();
        Integer prev2 = iterator.previous();

        assertThat(next1).isEqualTo(0);
        assertThat(next2).isEqualTo(1);
        assertThat(prev1).isEqualTo(1);
        assertThat(prev2).isEqualTo(0);
    }

    @Test
    void next_all_previous_all() {
        ArrayIterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9);
        ArrayList<Integer> actualNext = new ArrayList<>(10);
        while (iterator.hasNext()) {
            actualNext.add(iterator.next());
        }
        ArrayList<Integer> actualPrev = new ArrayList<>(10);
        while (iterator.hasPrevious()) {
            actualPrev.add(iterator.previous());
        }

        assertThat(actualNext).isEqualTo(List.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9));
        assertThat(actualPrev).isEqualTo(List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
    }

    @Test
    void set() {
        ArrayIterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9);
        while (iterator.hasNext()) {
            int next = iterator.next();
            iterator.set(next * next);
        }
        ArrayList<Integer> actual = new ArrayList<>(10);
        while (iterator.hasPrevious()) {
            actual.add(iterator.previous());
        }

        assertThat(actual).isEqualTo(List.of(81, 64, 49, 36, 25, 16, 9, 4, 1, 0));
    }
}
