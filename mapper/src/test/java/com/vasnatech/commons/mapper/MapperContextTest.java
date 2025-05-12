package com.vasnatech.commons.mapper;

import org.junit.jupiter.api.Test;

import java.time.Instant;

public class MapperContextTest {

    @Test
    void javaPrimitive() {
        MapperContext mapperContext = MapperContexts.javaPrimitive();

        Integer integerValue = mapperContext.map("12");
        Long longValue = mapperContext.map("12");

        System.out.println(integerValue);
        System.out.println(longValue);
    }

    @Test
    void javaTime() {
        MapperContext mapperContext = MapperContexts.javaTime();

        Instant instant = mapperContext.map("1970-01-01T00:00:00.000Z");

        System.out.println(instant);
    }

    @Test
    void compound() {
        MapperContext mapperContext = MapperContexts.compound(MapperContexts.javaPrimitive(), MapperContexts.javaTime());

        Integer integerValue = mapperContext.map("12");
        Long longValue = mapperContext.map("12");
        Instant instant = mapperContext.map("1970-01-01T00:00:00.000Z");

        System.out.println(integerValue);
        System.out.println(longValue);
        System.out.println(instant);
    }
}
