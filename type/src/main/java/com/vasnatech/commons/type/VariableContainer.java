package com.vasnatech.commons.type;

import java.util.Map;

public interface VariableContainer {

    boolean containsKey(String name);

    Object get(String name);

    Object put(String name, Object value);

    Object remove(String name);

    Map<String, Object> flattenAsMap();
}
