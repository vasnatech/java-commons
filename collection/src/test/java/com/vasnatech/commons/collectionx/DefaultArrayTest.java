package com.vasnatech.commons.collectionx;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultArrayTest {

    static Random RANDOM = new Random();
    static int TRIAL_COUNT = 25;

    Integer[] randomIntegers() {
        return RANDOM.ints(RANDOM.nextInt(1000)).boxed().toArray(Integer[]::new);
    }

    @Test
    void constructor_empty() {
        DefaultArray<Integer> array = new DefaultArray<>();
        assertThat(array.elements).isEqualTo(new Integer[0]);
    }

    @Test
    void constructor_some() {
        Integer[] source = randomIntegers();
        DefaultArray<Integer> array = new DefaultArray<>(source);
        assertThat(array.elements).isEqualTo(source);
    }

    @Test
    void of_empty() {
        DefaultArray<Integer> array = DefaultArray.of();
        assertThat(array.elements).isEqualTo(new Integer[0]);
    }

    @Test
    void of_some() {
        Integer[] source = randomIntegers();
        DefaultArray<Integer> array = DefaultArray.of(source);
        assertThat(array.elements).isEqualTo(source);
    }

    @Test
    void get_throws_index_out_of_bounds_exception_when_index_is_negative() {
        DefaultArray<Integer> array = new DefaultArray<>(randomIntegers());
        int trialCount = RANDOM.nextInt(TRIAL_COUNT);
        while (trialCount-- > 0) {
            assertThatThrownBy(() -> array.get(-1 - RANDOM.nextInt(123)))
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    @Test
    void get_throws_index_out_of_bounds_exception_when_index_is_greater_than_length() {
        Integer[] source = randomIntegers();
        DefaultArray<Integer> array = new DefaultArray<>(source);
        int trialCount = RANDOM.nextInt(TRIAL_COUNT);
        while (trialCount-- > 0) {
            assertThatThrownBy(() -> array.get(source.length + RANDOM.nextInt(123)))
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    @Test
    void get_returns() {
        Integer[] source = randomIntegers();
        DefaultArray<Integer> array = new DefaultArray<>(source);
        int trialCount = RANDOM.nextInt(TRIAL_COUNT);
        while (trialCount-- > 0) {
            int index = RANDOM.nextInt(source.length);
            assertThat(array.get(index)).isEqualTo(source[index]);
        }
    }

    @Test
    void getOptional_returns_empty_when_index_is_negative() {
        DefaultArray<Integer> array = new DefaultArray<>(randomIntegers());
        int trialCount = RANDOM.nextInt(TRIAL_COUNT);
        while (trialCount-- > 0) {
            assertThat(array.getOptional(-1 - RANDOM.nextInt(123))).isEmpty();
        }
    }

    @Test
    void getOptional_returns_empty_when_index_is_greater_than_length() {
        Integer[] source = randomIntegers();
        DefaultArray<Integer> array = new DefaultArray<>(source);
        int trialCount = RANDOM.nextInt(TRIAL_COUNT);
        while (trialCount-- > 0) {
            assertThat(array.getOptional(source.length + RANDOM.nextInt(123))).isEmpty();
        }
    }

    @Test
    void getOptional_returns_something() {
        Integer[] source = randomIntegers();
        DefaultArray<Integer> array = new DefaultArray<>(source);
        int trialCount = RANDOM.nextInt(TRIAL_COUNT);
        while (trialCount-- > 0) {
            int index = RANDOM.nextInt(source.length);
            assertThat(array.getOptional(index)).isNotEmpty().containsSame(source[index]);
        }
    }

    @Test
    void iterator() {
        Integer[] source = randomIntegers();
        DefaultArray<Integer> array = new DefaultArray<>(source);
        Iterator<Integer> iterator = array.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            assertThat(iterator.next()).isEqualTo(source[index++]);
        }
    }
}
