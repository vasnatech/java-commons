package com.vasnatech.commons.inject;

import com.google.common.reflect.ClassPath;
import com.vasnatech.commons.function.CachedSupplier;
import com.vasnatech.commons.function.CheckedFunction;
import com.vasnatech.commons.function.CheckedSupplier;
import com.vasnatech.commons.mapper.MapperContext;
import com.vasnatech.commons.mapper.MapperContexts;
import com.vasnatech.commons.resource.Resources;
import com.vasnatech.commons.yaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanContext {

    private static BeanContext INSTANCE;

    public static BeanContext getBeanContext() {
        return INSTANCE;
    }

    public static <T> T $P(String path, Class<T> clazz) {
        return INSTANCE.getProperty(path, clazz);
    }

    @SafeVarargs
    public static <T> T $P(String path, T... reified) {
        return INSTANCE.getProperty(path, reified);
    }

    public static <T> T $B(Class<T> clazz) {
        return INSTANCE.getBean(clazz);
    }

    @SafeVarargs
    public static <T> T $B(String name, T... reified) {
        return INSTANCE.getBean(name, reified);
    }

    @SafeVarargs
    public static <T> T $B(T... reified) {
        return INSTANCE.getBean(reified);
    }

    public static void init(String packageName) {
        init(packageName, MapperContexts.compound(MapperContexts.javaPrimitive(), MapperContexts.javaTime()));
    }

    public static void init(String packageName, MapperContext mapperContext) {
        if (INSTANCE != null) return;

        InputStream in = Resources.asInputStream("application.yaml");
        Map<String, ?> properties = in == null ? Map.of() : CheckedSupplier.get(() -> Yaml.decoder().fromInputStream(in, Map.class, String.class, Object.class));
        Map<String, Supplier<?>> beans = new HashMap<>();
        INSTANCE = new BeanContext(mapperContext, properties, beans);

        Set<Class<?>> allClasses = findAllClasses(packageName);

        Set<Class<?>> configClasses = new HashSet<>();
        Set<Class<?>> componentClasses = new HashSet<>();
        findAspects(allClasses, configClasses, componentClasses);

        for (Class<?> clazz : componentClasses) {
            Component componentAnnotation = clazz.getAnnotation(Component.class);
            String componentName = componentAnnotation.value();
            Supplier<Object> componentSupplier = toCachedSupplier(() -> createFromConstructor(getInjectConstructor(clazz)));
            if (componentName != null && !componentName.isEmpty()) {
                beans.put(componentName, componentSupplier);
            }
            beans.put(clazz.getName(), componentSupplier);
        }

        for (Class<?> clazz : configClasses) {
            Supplier<Object> configSupplier = toCachedSupplier(() -> createFromConstructor(getInjectConstructor(clazz)));
            String configName = clazz.getName();
            beans.put(configName, configSupplier);

            for (Method method : clazz.getDeclaredMethods()) {
                Component componentAnnotation = method.getAnnotation(Component.class);
                if (componentAnnotation != null) {
                    String componentName = componentAnnotation.value();
                    Supplier<Object> componentSupplier = toCachedSupplier(() -> createFromMethod(clazz, method));
                    if (componentName == null || componentName.isEmpty()) {
                        beans.put(method.getName(), componentSupplier);
                    } else {
                        beans.put(componentName, componentSupplier);
                    }
                    beans.put(method.getReturnType().getName(), componentSupplier);
                }
            }
        }
    }

    static <T, E extends Throwable> CachedSupplier<T> toCachedSupplier(CheckedSupplier<T, E> checkedSupplier) {
        return CachedSupplier.of(CheckedSupplier.unchecked(checkedSupplier));
    }

    static Set<Class<?>> findAllClasses(String packageName) {
        return CheckedSupplier.get(() -> ClassPath.from(BeanContext.class.getClassLoader()))
                .getTopLevelClassesRecursive(packageName)
                .stream()
                .map(ClassPath.ClassInfo::getName)
                .map(CheckedFunction.unchecked(Class::forName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    static void findAspects(Set<Class<?>> allClasses, Set<Class<?>> configs, Set<Class<?>> components) {
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(Config.class)) {
                configs.add(clazz);
            }
            if (clazz.isAnnotationPresent(Component.class)) {
                components.add(clazz);
            }
        }
    }

    private static Constructor<?> getInjectConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> injectConstructor = null;
        Constructor<?> defaultConstructor = null;
        if (constructors.length == 1) {
            injectConstructor = constructors[0];
        } else {
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == 0) {
                    defaultConstructor = constructor;
                }
                if (constructor.isAnnotationPresent(Inject.class)) {
                    if (injectConstructor != null) {
                        throw new IllegalStateException("Class: " + clazz.getName() + " has more that one @Inject constructor.");
                    }
                    injectConstructor = constructor;
                }
            }
        }
        if (injectConstructor == null) {
            injectConstructor = defaultConstructor;
        }
        if (injectConstructor == null) {
            throw new IllegalStateException("Class: " + clazz.getName() + " has no @Inject constructor.");
        }
        return injectConstructor;
    }

    static Object createFromConstructor(Constructor<?> constructor) throws Exception {
        Object[] parameters = Stream.of(constructor.getParameters())
                .map(BeanContext::getParameter)
                .toArray(Object[]::new);
        return constructor.newInstance(parameters);
    }

    static Object createFromMethod(Class<?> configClass, Method method) throws Exception {
        Object[] parameters = Stream.of(method.getParameters())
                .map(BeanContext::getParameter)
                .toArray(Object[]::new);
        return method.invoke(getBeanContext().getBean(configClass), parameters);
    }

    static Object getParameter(Parameter parameter) {
        Property property = parameter.getAnnotation(Property.class);
        if (property != null) {
            return getBeanContext().getProperty(property.value(), parameter.getType());
        }
        Bean bean = parameter.getAnnotation(Bean.class);
        if (bean != null) {
            String componentName = bean.value();
            if (componentName != null && !componentName.isEmpty()) {
                return getBeanContext().getBean(componentName, parameter.getType());
            }
        }
        return getBeanContext().getBean(parameter.getType());
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getClassOf(T[] array) {
        return (Class<T>) array.getClass().getComponentType();
    }


    Map<String, ?> properties;
    Map<String, Supplier<?>> beans;
    MapperContext mapperContext;

    BeanContext(MapperContext mapperContext, Map<String, ?> properties, Map<String, Supplier<?>> beans) {
        this.mapperContext = mapperContext;
        this.properties = properties;
        this.beans = beans;
    }

    private Object getProperty(String path) {
        if (path == null) return null;
        String[] keys = path.split("\\.");
        Object current = properties;
        for (String key : keys) {
            if (current == null) {
                return null;
            }
            if (current instanceof Map<?,?> map) {
                current = map.get(key);
            } else {
                return null;
            }
        }
        return current;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String path, Class<T> clazz) {
        Object value = getProperty(path);
        if (value == null) {
            return null;
        }
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        return mapperContext.map(value);
    }

    @SafeVarargs
    public final <T> T getProperty(String path, T... reified) {
        return getProperty(path, getClassOf(reified));
    }

    public <T> T getBean(Class<T> clazz) {
        return getBean(clazz.getName(), clazz);
    }

    public <T> T getBean(String name, Class<T> clazz) {
       return clazz.cast(beans.get(name).get());
    }

    @SafeVarargs
    public final <T> T getBean(String name, T... reified) {
        return getBean(name, getClassOf(reified));
    }

    @SafeVarargs
    public final <T> T getBean(T... reified) {
        Class<T> clazz = getClassOf(reified);
        return getBean(clazz.getName(), clazz);
    }
}
