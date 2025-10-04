package com.vasnatech.commons.argument.parser;

import java.util.Objects;

public class ArgDef<T> implements Comparable<ArgDef<T>> {

    private final Class<T> valueType;
    private final String name;
    private final String alias;
    private final String usage;
    private final boolean required;
    private final T defaultValue;

    public ArgDef(Class<T> type, String name, String alias, String usage, boolean required, T defaultValue) {
        this.valueType = type;
        this.name = name;
        this.alias = alias;
        this.usage = usage;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "ArgDef{" +
                "valueType=" + valueType +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", usage='" + usage + '\'' +
                ", required=" + required +
                ", defaultValue=" + defaultValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArgDef<?> argDef = (ArgDef<?>) o;
        return Objects.equals(name, argDef.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public int compareTo(ArgDef<T> o) {
        return this.name.compareTo(o.name);
    }

    public Class<T> getValueType() {
        return valueType;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getUsage() {
        return usage;
    }

    public boolean isRequired() {
        return required;
    }

    public T getDefaultValue() {
        return defaultValue;
    }



    public static <T> Builder<T> builder(Class<T> valueType) {
        Objects.requireNonNull(valueType, "Argument type must not be null");
        return new Builder<>(valueType);
    }

    public static <T> Builder<T> builder(T... reified) {
        return new Builder<T>(getClassOf(reified));
    }

    public static class Builder<T> {
        private final Class<T> valueType;
        private String name;
        private String alias;
        private String usage;
        private boolean required = false;
        private T defaultValue;

        public Builder(Class<T> valueType) {
            this.valueType = valueType;
        }

        public Builder<T> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<T> alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder<T> usage(String usage) {
            this.usage = usage;
            return this;
        }

        public Builder<T> required(boolean required) {
            this.required = required;
            return this;
        }

        public Builder<T> defaultValue(T defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public ArgDef<T> build() {
            if (name == null) {
                throw new IllegalArgumentException("Argument 'name' is required");
            }
            return new ArgDef<T>(valueType, name, alias, usage, required, defaultValue);
        }
    }


    @SuppressWarnings("unchecked")
    private static <T> Class<T> getClassOf(T[] array) {
        return (Class<T>) array.getClass().getComponentType();
    }
}
