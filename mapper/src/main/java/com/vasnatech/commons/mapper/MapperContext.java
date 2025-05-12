package com.vasnatech.commons.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vasnatech.commons.mapper.Util.classOf;

public interface MapperContext {

    <S> SourceMapper<S> source(Class<S> sourceClass);

    @SuppressWarnings("unchecked")
    default <S> SourceMapper<S> source(S... reified) {
        return source(classOf(reified));
    }

    default <S, T> Mapper<S, T> source(Class<S> sourceClass, Class<T> targetClass) {
        SourceMapper<S> sourceMapper = source(sourceClass);
        return sourceMapper.target(targetClass);
    }

    default <S, T> T map(Class<S> sourceClass, S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return Optional.of(sourceClass)
                .map(this::source)
                .map(sourceMapper -> sourceMapper.target(targetClass))
                .map(mapper -> mapper.map(source))
                .orElseThrow(() -> new IllegalArgumentException("Unable to map from " + sourceClass + " to " + targetClass));
    }

    @SuppressWarnings("unchecked")
    default <S, T> T map(Class<S> sourceClass, S source, T... reified) {
        return map(sourceClass, source, classOf(reified));
    }

    @SuppressWarnings("unchecked")
    default <S, T> T map(S source, T... reified) {
        if (source == null) {
            return null;
        }
        Class<S> sourceClass = (Class<S>) source.getClass();
        return map(sourceClass, source, classOf(reified));
    }

    static Builder builder() {
        return new Builder();
    }


    final class Builder {

        Map<Class<?>, Map<Class<?>, Mapper<?, ?>>> groupedBySourceAndTarget = new HashMap<>();

        public <S, T> Builder add(Class<S> sourceClass, Class<T> targetClass, Mapper<S, T> mapper) {
            Map<Class<?>, Mapper<?, ?>> groupedByTarget = groupedBySourceAndTarget.computeIfAbsent(sourceClass, key -> new HashMap<>());
            groupedByTarget.put(targetClass, mapper);
            return this;
        }

        @SafeVarargs
        public final <S, T> Builder add(Class<S> sourceClass, Mapper<S, T> mapper, T... reified) {
            add(sourceClass, classOf(reified), mapper);
            return this;
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        public MapperContext build() {
            return new ReadonlyMapperContext(
                    groupedBySourceAndTarget.entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    sourceEntry -> new SourceMapper.ReadonlySourceMapper(
                                            sourceEntry.getKey(),
                                            sourceEntry.getValue()
                                    )
                            ))
            );
        }

    }

    final class ReadonlyMapperContext implements MapperContext {

        private final Map<Class<?>, SourceMapper<?>> sourceMappers;

        public ReadonlyMapperContext(Map<Class<?>, SourceMapper<?>> sourceMappers) {
            this.sourceMappers = sourceMappers;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <S> SourceMapper<S> source(Class<S> sourceClass) {
            return (SourceMapper<S>) sourceMappers.get(sourceClass);
        }
    }

}
