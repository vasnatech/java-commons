package com.vasnatech.commons.type;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CachedVariableContainer implements VariableContainer {

    final Map<String, Object> variables;
    final List<VariableContainer> caches;

    public CachedVariableContainer() {
        this.variables = new LinkedHashMap<>();
        this.caches = new ArrayList<>();
    }

    public CachedVariableContainer(Map<String, Object> variables, List<VariableContainer> caches) {
        this.variables = new LinkedHashMap<>(variables);
        this.caches = new ArrayList<>(caches);
    }

    public CachedVariableContainer(Map<String, Object> variables, VariableContainer... caches) {
        this.variables = new LinkedHashMap<>(variables);
        this.caches = Stream.of(caches).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean containsKey(String name) {
        return variables.containsKey(name) || caches.stream().anyMatch(cache -> cache.containsKey(name));
    }

    @Override
    public Object get(String name) {
        return Optional.ofNullable(variables.get(name))
                .or(() -> caches.stream()
                        .filter(cache -> containsKey(name))
                        .map(cache -> cache.get(name))
                        .findFirst()
                )
                .orElse(null);
    }

    @Override
    public Object put(String name, Object value) {
        return variables.put(name, value);
    }

    @Override
    public Object remove(String name) {
        return variables.remove(name);
    }

    @Override
    public Map<String, Object> flattenAsMap() {
        TreeMap<String, Object> all = caches.reversed().stream()
                .map(VariableContainer::flattenAsMap)
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> x, TreeMap::new));
        all.putAll(variables);
        return all;
    }
}
