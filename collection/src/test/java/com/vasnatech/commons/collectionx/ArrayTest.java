package com.vasnatech.commons.collectionx;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayTest {

    static Random RANDOM = new Random();

    Integer[] randomIntegers() {
        return RANDOM.ints(RANDOM.nextInt(1000)).boxed().toArray(Integer[]::new);
    }

    @Test
    void of_empty() {
        Array<Integer> array = Array.of();
        assertThat(array.length()).isEqualTo(0);
    }

    @Test
    void of_some() {
        Integer[] source = randomIntegers();
        Array<Integer> array = Array.of(source);
        for(int index=0; index<source.length; ++index) {
            assertThat(array.get(index)).isEqualTo(source[index]);
        }
    }
}
