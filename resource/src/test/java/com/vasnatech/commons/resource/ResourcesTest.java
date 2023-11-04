package com.vasnatech.commons.resource;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ResourcesTest {

    @Test
    void asString_with_object() {
        SoftAssertions softly = new SoftAssertions();
        try {
            String actual = Resources.asString(this, "ResourcesTest.txt");
            softly.assertThat(actual).isEqualTo("The quick brown fox jumps over the lazy dog");
        } catch (IOException e) {
            softly.fail(e.getMessage(), e);
        }
        softly.assertAll();
    }

    @Test
    void asString_with_class() {
        SoftAssertions softly = new SoftAssertions();
        try {
            String actual = Resources.asString(ResourcesTest.class, "ResourcesTest.txt");
            softly.assertThat(actual).isEqualTo("The quick brown fox jumps over the lazy dog");
        } catch (IOException e) {
            softly.fail(e.getMessage(), e);
        }
        softly.assertAll();
    }
}
