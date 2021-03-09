package com.vasnatech.commons.random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomsTest {

    @Test
    void hex() {
        assertEquals(32, Randoms.hex().length());
    }

    @Test
    void hex_with_size() {
        assertEquals(  0, Randoms.hex( -1).length());
        assertEquals(  0, Randoms.hex(  0).length());
        assertEquals( 24, Randoms.hex( 12).length());
        assertEquals(256, Randoms.hex(128).length());
        assertEquals(256, Randoms.hex(222).length());
    }
}
