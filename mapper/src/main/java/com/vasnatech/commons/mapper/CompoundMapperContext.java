package com.vasnatech.commons.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CompoundMapperContext implements MapperContext{

    private final List<MapperContext> mapperContexts;

    public CompoundMapperContext(List<MapperContext> mapperContexts) {
        this.mapperContexts = mapperContexts == null ? List.of() : mapperContexts;
    }

    public CompoundMapperContext(MapperContext... mapperContexts) {
        this.mapperContexts = Stream.of(mapperContexts).toList();
    }

    @Override
    public <S> SourceMapper<S> source(Class<S> sourceClass) {
        return new CompoundSourceMapper<>(
                sourceClass,
                mapperContexts.stream()
                        .map(mapperContext -> mapperContext.source(sourceClass))
                        .filter(Objects::nonNull)
                        .toList()
        );
    }

    public static class CompoundSourceMapper<S> implements SourceMapper<S> {

        private final Class<S> sourceClass;
        private final List<SourceMapper<S>> sourceMappers;

        public CompoundSourceMapper(Class<S> sourceClass, List<SourceMapper<S>> sourceMappers) {
            this.sourceClass = sourceClass;
            this.sourceMappers = sourceMappers;
        }

        @Override
        public Class<S> sourceClass() {
            return sourceClass;
        }

        @Override
        public <T> Mapper<S, T> target(Class<T> targetClass) {
            return sourceMappers.stream()
                    .map(sourceMapper -> sourceMapper.target(targetClass))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }
    }
}
