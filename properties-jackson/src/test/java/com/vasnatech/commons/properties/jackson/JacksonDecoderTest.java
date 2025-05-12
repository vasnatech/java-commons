package com.vasnatech.commons.properties.jackson;

import com.vasnatech.commons.properties.Properties;
import com.vasnatech.commons.resource.Resources;
import com.vasnatech.commons.serialize.Decoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonDecoderTest {

    static final String TEST_01_PROPERTIES = "test01.properties";

    static Decoder decoder;
    static TestModel expectedObject;
    static Map<String, Object> expectedMap;

    @BeforeAll
    static void beforeAll() {
        PropertiesJackson.init();
        decoder = Properties.decoder();

        expectedObject = new TestModel(
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

        expectedMap = Map.ofEntries(
                Map.entry("booleanValue", "true"),
                Map.entry("stringValue", "abc"),
                Map.entry("charValue", "d"),
                Map.entry("byteValue", "1"),
                Map.entry("shortValue", "2"),
                Map.entry("integerValue", "3"),
                Map.entry("longValue", "4"),
                Map.entry("floatValue", "1.0"),
                Map.entry("doubleValue", "2.0"),
                Map.entry("bigIntegerValue", "5"),
                Map.entry("bigDecimalValue", "3.0"),
                Map.entry("obj", Map.ofEntries(
                        Map.entry("booleanValue", "true"),
                        Map.entry("stringValue", "abc"),
                        Map.entry("charValue", "d"),
                        Map.entry("byteValue", "1"),
                        Map.entry("shortValue", "2"),
                        Map.entry("integerValue", "3"),
                        Map.entry("longValue", "4"),
                        Map.entry("floatValue", "1.0"),
                        Map.entry("doubleValue", "2.0"),
                        Map.entry("bigIntegerValue", "5"),
                        Map.entry("bigDecimalValue", "3.0"),
                        Map.entry("obj", Map.ofEntries(
                                Map.entry("booleanValue", "true"),
                                Map.entry("stringValue", "abc"),
                                Map.entry("charValue", "d"),
                                Map.entry("byteValue", "1"),
                                Map.entry("shortValue", "2"),
                                Map.entry("integerValue", "3"),
                                Map.entry("longValue", "4"),
                                Map.entry("floatValue", "1.0"),
                                Map.entry("doubleValue", "2.0"),
                                Map.entry("bigIntegerValue", "5"),
                                Map.entry("bigDecimalValue", "3.0")
                        ))
                ))
        );
    }

    @Test
    void fromByteArray() throws IOException {
        byte[] bytes = Resources.asByteArray(this, TEST_01_PROPERTIES);
        TestModel actual = decoder.fromByteArray(bytes, TestModel.class);

        assertThat(actual).isEqualTo(expectedObject);
    }

    @Test
    void fromString() throws IOException {
        String content = Resources.asString(this, TEST_01_PROPERTIES);
        TestModel actual = decoder.fromString(content, TestModel.class);

        assertThat(actual).isEqualTo(expectedObject);
    }

    @Test
    void fromInputStream() throws IOException {
        InputStream in = Resources.asInputStream(this, TEST_01_PROPERTIES);
        TestModel actual = decoder.fromInputStream(in, TestModel.class);

        assertThat(actual).isEqualTo(expectedObject);
    }

    @Test
    void fromReader() throws IOException {
        Reader reader = Resources.asReader(this, TEST_01_PROPERTIES);
        TestModel actual = decoder.fromReader(reader, TestModel.class);

        assertThat(actual).isEqualTo(expectedObject);
    }

    @Test
    void fromByteArrayToMap() throws IOException {
        byte[] bytes = Resources.asByteArray(this, TEST_01_PROPERTIES);
        Map<String, ?> actual = decoder.fromByteArrayToMap(bytes);

        assertThat(actual).isEqualTo(expectedMap);
    }

    @Test
    void fromStringToMap() throws IOException {
        String content = Resources.asString(this, TEST_01_PROPERTIES);
        Map<String, ?> actual = decoder.fromStringToMap(content);

        assertThat(actual).isEqualTo(expectedMap);
    }

    @Test
    void fromInputStreamToMap() throws IOException {
        InputStream in = Resources.asInputStream(this, TEST_01_PROPERTIES);
        Map<String, ?> actual = decoder.fromInputStreamToMap(in);

        assertThat(actual).isEqualTo(expectedMap);
    }

    @Test
    void fromReaderToMap() throws IOException {
        Reader reader = Resources.asReader(this, TEST_01_PROPERTIES);
        Map<String, ?> actual = decoder.fromReaderToMap(reader);

        assertThat(actual).isEqualTo(expectedMap);
    }

    @Test
    void deserializeByteArrayAsObject() throws IOException {
        byte[] bytes = Resources.asByteArray(this, TEST_01_PROPERTIES);
        TestModel actual = decoder.deserialize(bytes, TestModel.class);

        assertThat(actual).isEqualTo(expectedObject);
    }

    @Test
    void deserializeByteArrayAsMap() throws IOException {
        byte[] bytes = Resources.asByteArray(this, TEST_01_PROPERTIES);
        Map<String, ?> actual = decoder.deserialize(bytes, Map.class, String.class, Object.class);

        assertThat(actual).isEqualTo(expectedMap);
    }

    @Test
    void deserializeInputStreamAsObject() throws IOException {
        InputStream in = Resources.asInputStream(this, TEST_01_PROPERTIES);
        TestModel actual = decoder.deserialize(in, TestModel.class);

        assertThat(actual).isEqualTo(expectedObject);
    }

    @Test
    void deserializeInputStreamArrayAsMap() throws IOException {
        InputStream in = Resources.asInputStream(this, TEST_01_PROPERTIES);
        Map<String, ?> actual = decoder.deserialize(in, Map.class, String.class, Object.class);

        assertThat(actual).isEqualTo(expectedMap);
    }
}
