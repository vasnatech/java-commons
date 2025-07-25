package com.vasnatech.commons.inject;

import com.vasnatech.commons.function.CheckedRunnable;
import com.vasnatech.commons.mapper.MapperContext;
import com.vasnatech.commons.mapper.MapperContexts;

import java.lang.reflect.Field;
import java.util.SortedSet;

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

    public static void init(SortedSet<String> packageNames) {
        init(MapperContexts.compound(MapperContexts.javaPrimitive(), MapperContexts.javaTime()), packageNames);
    }

    public static void init(MapperContext mapperContext, SortedSet<String> packageNames) {
        PropertyContext.init(mapperContext);
        BeanContext.init(packageNames);
    }

    public static void injectFields(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Bean.class)) {
                Bean bean = field.getAnnotation(Bean.class);
                String beanName = bean.value();
                Object beanInstance;
                if (beanName.isEmpty()) {
                    beanInstance = BeanContext.getBeanContext().getBean(field.getType());
                } else {
                    beanInstance = BeanContext.getBeanContext().getBean(beanName, field.getType());
                }
                field.setAccessible(true);
                CheckedRunnable.run(() -> field.set(instance, beanInstance));
            }
            if (field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                String propertyName = property.value();
                Object propertyValue = PropertyContext.getPropertyContext().getProperty(propertyName, field.getType());
                field.setAccessible(true);
                CheckedRunnable.run(() -> field.set(instance, propertyValue));
            }
        }
    }
}
