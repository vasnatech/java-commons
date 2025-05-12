package com.vasnatech.commons.properties.jackson;

import com.vasnatech.commons.properties.Properties;
import com.vasnatech.commons.resource.Resources;
import com.vasnatech.commons.serialize.Encoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonEncoderTest {

    static final String TEST_01_PROPERTIES = "test01.properties";

    static Encoder encoder;
    static TestModel object;
    static Map<String, Object> map;

    @BeforeAll
    static void beforeAll() {
        PropertiesJackson.init();
        encoder = Properties.encoder();

        object = new TestModel(
                true,
                "abc",
                'd',
                (byte)1,
                (short)2,
                3,
                4L,
                1.0F,
                2.0,
                new BigInteger("5"),
                new BigDecimal("3.0"),
                new TestModel(
                        true,
                        "abc",
                        'd',
                        (byte)1,
                        (short)2,
                        3,
                        4L,
                        1.0F,
                        2.0,
                        new BigInteger("5"),
                        new BigDecimal("3.0"),
                        new TestModel(
                                true,
                                "abc",
                                'd',
                                (byte)1,
                                (short)2,
                                3,
                                4L,
                                1.0F,
                                2.0,
                                new BigInteger("5"),
                                new BigDecimal("3.0"),
                                null
                        )
                )
        );

        map = Map.ofEntries(
                Map.entry("booleanValue", true),
                Map.entry("stringValue", "abc"),
                Map.entry("charValue", "d"),
                Map.entry("byteValue", 1),
                Map.entry("shortValue", (short) 2),
                Map.entry("integerValue", 3),
                Map.entry("longValue", 4L),
                Map.entry("floatValue", 1.0F),
                Map.entry("doubleValue", 2.0),
                Map.entry("bigIntegerValue", new BigInteger("5")),
                Map.entry("bigDecimalValue", new BigDecimal("3.0")),
                Map.entry("obj", Map.ofEntries(
                        Map.entry("booleanValue", true),
                        Map.entry("stringValue", "abc"),
                        Map.entry("charValue", "d"),
                        Map.entry("byteValue", 1),
                        Map.entry("shortValue", (short) 2),
                        Map.entry("integerValue", 3),
                        Map.entry("longValue", 4L),
                        Map.entry("floatValue", 1.0F),
                        Map.entry("doubleValue", 2.0),
                        Map.entry("bigIntegerValue", new BigInteger("5")),
                        Map.entry("bigDecimalValue", new BigDecimal("3.0")),
                        Map.entry("obj", Map.ofEntries(
                                Map.entry("booleanValue", true),
                                Map.entry("stringValue", "abc"),
                                Map.entry("charValue", "d"),
                                Map.entry("byteValue", 1),
                                Map.entry("shortValue", (short) 2),
                                Map.entry("integerValue", 3),
                                Map.entry("longValue", 4L),
                                Map.entry("floatValue", 1.0F),
                                Map.entry("doubleValue", 2.0),
                                Map.entry("bigIntegerValue", new BigInteger("5")),
                                Map.entry("bigDecimalValue", new BigDecimal("3.0"))
                        ))
                ))
        );
    }

    @Test
    void encodeToMap() throws IOException {
        Map<String, Object> actual = encoder.encode(object, Map.class, String.class, Object.class);
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(map);
    }

    @Test
    void objectToString() throws IOException {
        String expected = Resources.asString(this, TEST_01_PROPERTIES);
        String actual = encoder.toString(object);

        assertThat(actual).isEqualTo(expected);
    }
}
