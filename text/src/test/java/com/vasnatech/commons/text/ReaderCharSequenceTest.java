package com.vasnatech.commons.text;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class ReaderCharSequenceTest {

    @Test
    void test() {
        StringReader reader = new StringReader("0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef");
        ReaderCharSequence sequence = new ReaderCharSequence(reader, 16);

        for (int i=0; i<64; ++i) {
            assertThat(Character.digit(sequence.charAt(i), 16)).isEqualTo(i % 16);
        }
    }
}
