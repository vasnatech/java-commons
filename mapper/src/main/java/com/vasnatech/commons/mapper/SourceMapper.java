package com.vasnatech.commons.mapper;

import java.util.Map;

import static com.vasnatech.commons.mapper.Util.classOf;

public interface SourceMapper<S> {

    Class<S> sourceClass();

    <T> Mapper<S, T> target(Class<T> targetClass);

    @SuppressWarnings("unchecked")
    default <T> Mapper<S, ? extends T> target(T... reified) {
        return target(classOf(reified));
    }


    final class ReadonlySourceMapper<S> implements SourceMapper<S> {

        private final Class<S> sourceClass;
        private final Map<Class<?>, Mapper<S, ?>> mappers;

        public ReadonlySourceMapper(Class<S> sourceClass, Map<Class<?>, Mapper<S, ?>> mappers) {
            this.sourceClass = sourceClass;
            this.mappers = mappers;
        }

        @Override
        public Class<S> sourceClass() {
            return sourceClass;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> Mapper<S, T> target(Class<T> targetClass) {
            return (Mapper<S, T>) mappers.get(targetClass);
        }
    }
}
