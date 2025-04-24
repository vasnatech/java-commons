package com.vasnatech.commons.properties.jackson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class TestModel {
    Boolean booleanValue;
    String stringValue;
    Character charValue;
    Byte byteValue;
    Short shortValue;
    Integer integerValue;
    Long longValue;
    Float floatValue;
    Double doubleValue;
    BigInteger bigIntegerValue;
    BigDecimal bigDecimalValue;
    TestModel obj;

    public TestModel() {}

    public TestModel(
            Boolean booleanValue,
            String stringValue,
            Character charValue,
            Byte byteValue,
            Short shortValue,
            Integer integerValue,
            Long longValue,
            Float floatValue,
            Double doubleValue,
            BigInteger bigIntegerValue,
            BigDecimal bigDecimalValue,
            TestModel obj
    ) {
        this.booleanValue = booleanValue;
        this.stringValue = stringValue;
        this.charValue = charValue;
        this.byteValue = byteValue;
        this.shortValue = shortValue;
        this.integerValue = integerValue;
        this.longValue = longValue;
        this.floatValue = floatValue;
        this.doubleValue = doubleValue;
        this.bigIntegerValue = bigIntegerValue;
        this.bigDecimalValue = bigDecimalValue;
        this.obj = obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestModel testModel)) return false;
        return Objects.equals(booleanValue, testModel.booleanValue)
                && Objects.equals(stringValue, testModel.stringValue)
                && Objects.equals(charValue, testModel.charValue)
                && Objects.equals(byteValue, testModel.byteValue)
                && Objects.equals(shortValue, testModel.shortValue)
                && Objects.equals(integerValue, testModel.integerValue)
                && Objects.equals(longValue, testModel.longValue)
                && Objects.equals(floatValue, testModel.floatValue)
                && Objects.equals(doubleValue, testModel.doubleValue)
                && Objects.equals(bigIntegerValue, testModel.bigIntegerValue)
                && Objects.equals(bigDecimalValue, testModel.bigDecimalValue)
                && Objects.equals(obj, testModel.obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                booleanValue,
                stringValue,
                charValue,
                byteValue,
                shortValue,
                integerValue,
                longValue,
                floatValue,
                doubleValue,
                bigIntegerValue,
                bigDecimalValue,
                obj
        );
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "booleanValue=" + booleanValue +
                ", stringValue='" + stringValue + '\'' +
                ", charValue=" + charValue +
                ", byteValue=" + byteValue +
                ", shortValue=" + shortValue +
                ", integerValue=" + integerValue +
                ", longValue=" + longValue +
                ", floatValue=" + floatValue +
                ", doubleValue=" + doubleValue +
                ", bigIntegerValue=" + bigIntegerValue +
                ", bigDecimalValue=" + bigDecimalValue +
                ", obj=" + obj +
                '}';
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Character getCharValue() {
        return charValue;
    }

    public void setCharValue(Character charValue) {
        this.charValue = charValue;
    }

    public Byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(Byte byteValue) {
        this.byteValue = byteValue;
    }

    public Short getShortValue() {
        return shortValue;
    }

    public void setShortValue(Short shortValue) {
        this.shortValue = shortValue;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public BigInteger getBigIntegerValue() {
        return bigIntegerValue;
    }

    public void setBigIntegerValue(BigInteger bigIntegerValue) {
        this.bigIntegerValue = bigIntegerValue;
    }

    public BigDecimal getBigDecimalValue() {
        return bigDecimalValue;
    }

    public void setBigDecimalValue(BigDecimal bigDecimalValue) {
        this.bigDecimalValue = bigDecimalValue;
    }

    public TestModel getObj() {
        return obj;
    }

    public void setObj(TestModel obj) {
        this.obj = obj;
    }
}
