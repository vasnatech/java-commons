package com.vasnatech.commons.random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomsTest {

    @Test
    void hex() {
        assertEquals(32, Randoms.hex().length());
    }

    @ParameterizedTest
    @CsvSource({
            "0,-1",
            "0,0",
            "24,12",
            "256,128",
            "256,222",
    })
    void hex_with_size(int expectedLength, int size) {
        assertEquals(  expectedLength, Randoms.hex( size).length());
        assertEquals(  0, Randoms.hex( -1).length());
        assertEquals(  0, Randoms.hex(  0).length());
        assertEquals( 24, Randoms.hex( 12).length());
        assertEquals(256, Randoms.hex(128).length());
        assertEquals(256, Randoms.hex(222).length());
    }
}
