package com.vasnatech.commons.inject;

import com.google.common.reflect.ClassPath;
import com.vasnatech.commons.function.CachedSupplier;
import com.vasnatech.commons.function.CheckedFunction;
import com.vasnatech.commons.function.CheckedSupplier;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.SequencedSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanContext {

    private static BeanContext INSTANCE;

    public static BeanContext getBeanContext() {
        return INSTANCE;
    }

    public static void init(SequencedSet<String> packageNames) {
        if (INSTANCE != null) return;

        Map<String, Supplier<?>> beans = new HashMap<>();
        INSTANCE = new BeanContext(beans);

        SequencedSet<Class<?>> allClasses = packageNames.stream()
                .map(BeanContext::findClasses)
                .flatMap(SequencedSet::stream)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        SequencedSet<Class<?>> configClasses = new LinkedHashSet<>();
        SequencedSet<Class<?>> componentClasses = new LinkedHashSet<>();
        findAspects(allClasses, configClasses, componentClasses);

        for (Class<?> clazz : componentClasses) {
            Component componentAnnotation = clazz.getAnnotation(Component.class);
            Supplier<Object> componentSupplier = toCachedSupplier(() -> createFromConstructor(getInjectConstructor(clazz), true));
            Stream.of(componentAnnotation.value()).forEach(componentName -> beans.put(componentName, componentSupplier));
            beans.put(clazz.getName(), componentSupplier);
        }

        for (Class<?> clazz : configClasses) {
            Supplier<Object> configSupplier = toCachedSupplier(() -> createFromConstructor(getInjectConstructor(clazz), true));
            String configName = clazz.getName();
            beans.put(configName, configSupplier);

            for (Method method : clazz.getDeclaredMethods()) {
                Component componentAnnotation = method.getAnnotation(Component.class);
                if (componentAnnotation != null) {
                    Supplier<Object> componentSupplier = toCachedSupplier(() -> createFromMethod(clazz, method));
                    if (componentAnnotation.value().length == 0) {
                        beans.put(method.getName(), componentSupplier);
                    } else {
                        Stream.of(componentAnnotation.value()).forEach(componentName -> beans.put(componentName, componentSupplier));
                    }
                    beans.put(method.getReturnType().getName(), componentSupplier);
                }
            }
        }
    }

    static <T, E extends Throwable> CachedSupplier<T> toCachedSupplier(CheckedSupplier<T, E> checkedSupplier) {
        return CachedSupplier.of(CheckedSupplier.unchecked(checkedSupplier));
    }

    static SequencedSet<Class<?>> findClasses(String packageName) {
        return CheckedSupplier.get(() -> ClassPath.from(BeanContext.class.getClassLoader()))
                .getTopLevelClassesRecursive(packageName)
                .stream()
                .map(ClassPath.ClassInfo::getName)
                .map(CheckedFunction.unchecked(Class::forName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    static void findAspects(SequencedSet<Class<?>> allClasses, SequencedSet<Class<?>> configs, SequencedSet<Class<?>> components) {
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

    static Object createFromConstructor(Constructor<?> constructor, boolean injectFields) throws Exception {
        Object[] parameters = Stream.of(constructor.getParameters())
                .map(BeanContext::getParameter)
                .toArray(Object[]::new);
        Object object = constructor.newInstance(parameters);
        if (injectFields) {
            InjectContext.injectFields(object);
        }
        return object;
    }

    static Object createFromMethod(Class<?> configClass, Method method) throws Exception {
        Object[] parameters = Stream.of(method.getParameters())
                .map(BeanContext::getParameter)
                .toArray(Object[]::new);
        Object object = method.invoke(getBeanContext().getBean(configClass), parameters);
        return object;
    }

    static Object getParameter(Parameter parameter) {
        Property property = parameter.getAnnotation(Property.class);
        if (property != null) {
            return PropertyContext.getPropertyContext().getProperty(property.value(), parameter.getType());
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


    Map<String, Supplier<?>> beans;

    BeanContext(Map<String, Supplier<?>> beans) {
        this.beans = beans;
    }

    public <T> T getBean(String name, Class<T> clazz) {
        return Optional.ofNullable(beans.get(name))
                .map(Supplier::get)
                .map(clazz::cast)
                .orElse(null);
    }

    public <T> T getBean(Class<T> clazz) {
        return getBean(clazz.getName(), clazz);
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
