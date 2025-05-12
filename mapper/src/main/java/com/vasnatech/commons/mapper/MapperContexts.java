package com.vasnatech.commons.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapperContexts {

    static final Map<String, MapperContext> MAPPER_CONTEXTS = new ConcurrentHashMap<>();

    public static void register(String name, MapperContext mapperContext) {
        MAPPER_CONTEXTS.put(name, mapperContext);
    }

    public static MapperContext get(String name) {
        return MAPPER_CONTEXTS.get(name);
    }

    public static MapperContext javaPrimitive() {
        return MAPPER_CONTEXTS.computeIfAbsent(
                "javaPrimitive",
                key -> MapperContext.builder()
                        .add(String.class, Boolean::valueOf)
                        .add(String.class, Byte::valueOf)
                        .add(String.class, Short::valueOf)
                        .add(String.class, Integer::valueOf)
                        .add(String.class, Long::valueOf)
                        .add(String.class, Float::valueOf)
                        .add(String.class, Double::valueOf)
                        .add(String.class, BigInteger::new)
                        .add(String.class, BigDecimal::new)

                        .add(Boolean.class, Object::toString)
                        .add(Boolean.class, it -> it ? (byte) 1 : (byte) 0)
                        .add(Boolean.class, it -> it ? (short) 1 : (short) 0)
                        .add(Boolean.class, it -> it ? 1 : 0)
                        .add(Boolean.class, it -> it ? 1L : 0L)
                        .add(Boolean.class, it -> it ? 1.0f : 0.0f)
                        .add(Boolean.class, it -> it ? 1.0 : 0.0)
                        .add(Boolean.class, it -> it ? BigInteger.ONE : BigInteger.ZERO)
                        .add(Boolean.class, it -> it ? BigDecimal.ONE : BigDecimal.ZERO)

                        .add(Byte.class, Object::toString)
                        .add(Byte.class, it -> it != 0)
                        .add(Byte.class, Number::shortValue)
                        .add(Byte.class, Number::intValue)
                        .add(Byte.class, Number::longValue)
                        .add(Byte.class, Number::floatValue)
                        .add(Byte.class, Number::doubleValue)
                        .add(Byte.class, it -> BigInteger.valueOf(it.longValue()))
                        .add(Byte.class, it -> BigDecimal.valueOf(it.longValue()))

                        .add(Short.class, Object::toString)
                        .add(Short.class, it -> it != 0)
                        .add(Short.class, Number::byteValue)
                        .add(Short.class, Number::intValue)
                        .add(Short.class, Number::longValue)
                        .add(Short.class, Number::floatValue)
                        .add(Short.class, Number::doubleValue)
                        .add(Short.class, it -> BigInteger.valueOf(it.longValue()))
                        .add(Short.class, it -> BigDecimal.valueOf(it.longValue()))

                        .add(Integer.class, Object::toString)
                        .add(Integer.class, it -> it != 0)
                        .add(Integer.class, Number::byteValue)
                        .add(Integer.class, Number::shortValue)
                        .add(Integer.class, Number::longValue)
                        .add(Integer.class, Number::floatValue)
                        .add(Integer.class, Number::doubleValue)
                        .add(Integer.class, it -> BigInteger.valueOf(it.longValue()))
                        .add(Integer.class, it -> BigDecimal.valueOf(it.longValue()))

                        .add(Long.class, Object::toString)
                        .add(Long.class, it -> it != 0)
                        .add(Long.class, Number::byteValue)
                        .add(Long.class, Number::shortValue)
                        .add(Long.class, Number::intValue)
                        .add(Long.class, Number::floatValue)
                        .add(Long.class, Number::doubleValue)
                        .add(Long.class, BigInteger::valueOf)
                        .add(Long.class, BigDecimal::valueOf)

                        .add(Float.class, Object::toString)
                        .add(Float.class, it -> it != 0)
                        .add(Float.class, Number::byteValue)
                        .add(Float.class, Number::shortValue)
                        .add(Float.class, Number::intValue)
                        .add(Float.class, Number::longValue)
                        .add(Float.class, Number::doubleValue)
                        .add(Float.class, it -> BigInteger.valueOf(it.longValue()))
                        .add(Float.class, it -> BigDecimal.valueOf(it.doubleValue()))

                        .add(Double.class, Object::toString)
                        .add(Double.class, it -> it != 0)
                        .add(Double.class, Number::byteValue)
                        .add(Double.class, Number::shortValue)
                        .add(Double.class, Number::intValue)
                        .add(Double.class, Number::longValue)
                        .add(Double.class, Number::floatValue)
                        .add(Double.class, it -> BigInteger.valueOf(it.longValue()))
                        .add(Double.class, BigDecimal::valueOf)

                        .add(BigInteger.class, Object::toString)
                        .add(BigInteger.class, it -> it.signum() != 0)
                        .add(BigInteger.class, Number::byteValue)
                        .add(BigInteger.class, Number::shortValue)
                        .add(BigInteger.class, Number::intValue)
                        .add(BigInteger.class, Number::longValue)
                        .add(BigInteger.class, Number::floatValue)
                        .add(BigInteger.class, Number::doubleValue)
                        .add(BigInteger.class, BigDecimal::new)

                        .add(BigDecimal.class, Object::toString)
                        .add(BigDecimal.class, it -> it.signum() != 0)
                        .add(BigDecimal.class, Number::byteValue)
                        .add(BigDecimal.class, Number::shortValue)
                        .add(BigDecimal.class, Number::intValue)
                        .add(BigDecimal.class, Number::longValue)
                        .add(BigDecimal.class, Number::floatValue)
                        .add(BigDecimal.class, Number::doubleValue)
                        .add(BigDecimal.class, BigDecimal::toBigInteger)

                        .build()
        );
    }

    public static MapperContext javaTime() {
        return MAPPER_CONTEXTS.computeIfAbsent(
                "javaTime",
                key -> MapperContext.builder()
                        .add(String.class, Instant::parse)
                        .add(String.class, LocalDate::parse)
                        .add(String.class, LocalDateTime::parse)
                        .add(String.class, ZonedDateTime::parse)

                        .add(Long.class, Instant::ofEpochMilli)
                        .add(Long.class, it -> LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneOffset.UTC))
                        .add(Long.class, it -> LocalDate.ofInstant(Instant.ofEpochMilli(it), ZoneOffset.UTC))
                        .add(Long.class, it -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneOffset.UTC))

                        .add(Instant.class, Object::toString)
                        .add(Instant.class, Instant::toEpochMilli)
                        .add(Instant.class, it -> LocalDate.ofInstant(it, ZoneOffset.UTC))
                        .add(Instant.class, it -> LocalDateTime.ofInstant(it, ZoneOffset.UTC))
                        .add(Instant.class, it -> ZonedDateTime.ofInstant(it, ZoneOffset.UTC))

                        .add(LocalDate.class, Object::toString)
                        .add(LocalDate.class, it -> it.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000L)
                        .add(LocalDate.class, it -> Instant.ofEpochSecond(it.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC)))
                        .add(LocalDate.class, it -> LocalDateTime.of(it, LocalTime.MIN))
                        .add(LocalDate.class, it -> ZonedDateTime.of(it, LocalTime.MIN, ZoneOffset.UTC))

                        .add(LocalDateTime.class, Object::toString)
                        .add(LocalDateTime.class, it -> it.toEpochSecond(ZoneOffset.UTC) * 1000L)
                        .add(LocalDateTime.class, it -> it.toInstant(ZoneOffset.UTC))
                        .add(LocalDateTime.class, LocalDateTime::toLocalDate)
                        .add(LocalDateTime.class, it -> ZonedDateTime.of(it, ZoneOffset.UTC))

                        .add(ZonedDateTime.class, Object::toString)
                        .add(ZonedDateTime.class, it -> it.toEpochSecond() * 1000L)
                        .add(ZonedDateTime.class, ZonedDateTime::toInstant)
                        .add(ZonedDateTime.class, ZonedDateTime::toLocalDate)
                        .add(ZonedDateTime.class, ZonedDateTime::toLocalDateTime)

                        .build()
        );
    }

    public static MapperContext compound(MapperContext... mapperContexts) {
        return new CompoundMapperContext(mapperContexts);
    }
}
