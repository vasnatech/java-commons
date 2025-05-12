package com.vasnatech.commons.inject;

import com.vasnatech.commons.mapper.MapperContext;
import com.vasnatech.commons.mapper.MapperContexts;

public class InjectContext {

    public static <T> T $P(String path, Class<T> clazz) {
        return PropertyContext.getPropertyContext().getProperty(path, clazz);
    }

    @SafeVarargs
    public static <T> T $P(String path, T... reified) {
        return PropertyContext.getPropertyContext().getProperty(path, reified);
    }

    public static <T> T $B(Class<T> clazz) {
        return BeanContext.getBeanContext().getBean(clazz);
    }

    @SafeVarargs
    public static <T> T $B(String name, T... reified) {
        return BeanContext.getBeanContext().getBean(name, reified);
    }

    @SafeVarargs
    public static <T> T $B(T... reified) {
        return BeanContext.getBeanContext().getBean(reified);
    }

    public static void init(String packageName) {
        init(MapperContexts.compound(MapperContexts.javaPrimitive(), MapperContexts.javaTime()), packageName);
    }

    public static void init(MapperContext mapperContext, String packageName) {
        PropertyContext.init(mapperContext);
        BeanContext.init(packageName);
    }
}
