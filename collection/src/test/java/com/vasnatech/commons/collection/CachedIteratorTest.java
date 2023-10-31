package com.vasnatech.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CachedIteratorTest {

    @Test
    void cacheSize() {
        Iterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        assertThat(CachedIterator.of(iterator).cacheSize()).isEqualTo(Integer.MAX_VALUE);
        assertThat(CachedIterator.of(iterator, -10).cacheSize()).isEqualTo(0);
        assertThat(CachedIterator.of(iterator, 0).cacheSize()).isEqualTo(0);
        assertThat(CachedIterator.of(iterator, 10).cacheSize()).isEqualTo(10);
    }

    @Test
    void hasNext_next() {
        Iterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        CachedIterator<Integer> cachedIterator = CachedIterator.of(iterator);
        ArrayList<Integer> actual = new ArrayList<>(10);
        while (cachedIterator.hasNext()) {
            actual.add(cachedIterator.next());
        }
        assertThat(actual).isEqualTo(List.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9));
    }

    @Test
    void hasPrevious_previous() {
        ArrayIterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9);
        CachedIterator<Integer> cachedIterator = CachedIterator.of(iterator);
        ArrayList<Integer> actual = new ArrayList<>(10);
        while (cachedIterator.hasNext()) {
            cachedIterator.next();
        }
        while (cachedIterator.hasPrevious()) {
            actual.add(cachedIterator.previous());
        }
        assertThat(actual).isEqualTo(List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
    }

    @Test
    void next_previous() {
        ArrayIterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9);
        CachedIterator<Integer> cachedIterator = CachedIterator.of(iterator);

        Integer next1 = cachedIterator.next();
        Integer next2 = cachedIterator.next();
        Integer prev1 = cachedIterator.previous();
        Integer prev2 = cachedIterator.previous();

        assertThat(next1).isEqualTo(0);
        assertThat(next2).isEqualTo(1);
        assertThat(prev1).isEqualTo(1);
        assertThat(prev2).isEqualTo(0);
    }

    @Test
    void next_previous_with_fixed_size() {
        ArrayIterator<Integer> iterator = ArrayIterator.of(0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9);
        CachedIterator<Integer> cachedIterator = CachedIterator.of(iterator, 2);

        Integer next1 = cachedIterator.next();
        Integer next2 = cachedIterator.next();
        Integer next3 = cachedIterator.next();
        Integer prev1 = cachedIterator.previous();
        Integer prev2 = cachedIterator.previous();

        assertThat(next1).isEqualTo(0);
        assertThat(next2).isEqualTo(1);
        assertThat(next3).isEqualTo(2);
        assertThat(prev1).isEqualTo(2);
        assertThat(prev2).isEqualTo(1);
        assertThat(cachedIterator.hasPrevious()).isFalse();
    }
}
