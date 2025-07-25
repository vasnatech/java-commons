package com.vasnatech.commons.argument.parser;

import com.vasnatech.commons.mapper.MapperContext;
import com.vasnatech.commons.mapper.MapperContexts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArgDefs {

    private final MapperContext mapperContext;
    private final Map<String, ArgDef<?>> argDefsPerName;
    private final Map<String, ArgDef<?>> argDefsPerAlias;

    public ArgDefs(MapperContext mapperContext, ArgDef<?>... argDefs) {
        this(mapperContext, Stream.of(argDefs).collect(Collectors.toSet()));
    }

    public ArgDefs(MapperContext mapperContext, Set<ArgDef<?>> argDefs) {
        this.mapperContext = mapperContext;
        this.argDefsPerName = argDefs.stream()
                .collect(Collectors.toMap(
                        ArgDef::getName,
                        argDef -> argDef
                ));
        this.argDefsPerAlias = argDefs.stream()
                .filter(argDef -> argDef.getAlias() != null)
                .collect(Collectors.toMap(
                        ArgDef::getAlias,
                        argDef -> argDef
                ));
    }

    public Map<String, ?> parse(String... args) {
        Map<String, Object> arguments = new HashMap<>();
        Iterator<String> iterator = Stream.of(args).iterator();
        while (iterator.hasNext()) {
            String argName = iterator.next();
            ArgDef<?> argDef = getArgDef(argName);
            if (argDef == null) {
                throw new IllegalStateException("Argument '" + argName + "' was not expected.");
            }
            if (!iterator.hasNext()) {
                throw new IllegalStateException("Argument '" + argName + "' was not expected.");
            }
            String argStringValue = iterator.next();
            Object argValue = mapperContext.map(argStringValue, argDef.getValueType());

            arguments.put(argName, argValue);
        }

        for (ArgDef<?> argDef : argDefsPerAlias.values()) {
            Object argValue = arguments.get(argDef.getName());
            if (argValue == null) {
                if (argDef.isRequired()) {
                    throw new IllegalStateException("Argument '" + argDef.getName() + "' is required.");
                }
                arguments.put(argDef.getName(), argDef.getDefaultValue());
            }
        }

        return arguments;
    }

    private ArgDef<?> getArgDef(String argName) {
        return argDefsPerName.containsKey(argName)
                ? argDefsPerName.get(argName)
                : argDefsPerAlias.get(argName);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MapperContext mapperContext;
        private Set<ArgDef<?>> argDefs = new HashSet<>();

        public Builder mapperContext(MapperContext mapperContext) {
            this.mapperContext = mapperContext;
            return this;
        }

        public Builder add(ArgDef<?> argDef) {
            argDefs.add(argDef);
            return this;
        }

        public <T> Builder add(ArgDef.Builder<T> argDefBuilder) {
            return add(argDefBuilder.build());
        }

        public <T> Builder add(Consumer<ArgDef.Builder<T>> consumer) {
            ArgDef.Builder<T> argDefBuilder = ArgDef.builder();
            consumer.accept(argDefBuilder);
            return add(argDefBuilder.build());
        }

        public ArgDefs build() {
            return new ArgDefs(
                    mapperContext == null
                            ? MapperContexts.compound(MapperContexts.javaPrimitive(), MapperContexts.javaTime())
                            : mapperContext,
                    argDefs
            );
        }
    }
}
