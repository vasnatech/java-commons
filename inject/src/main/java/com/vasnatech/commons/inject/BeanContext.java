package com.vasnatech.commons.inject;

import com.google.common.reflect.ClassPath;
import com.vasnatech.commons.function.CheckedFunction;
import com.vasnatech.commons.function.CheckedSupplier;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanContext {

    public static BeanContext $;

    public static BeanContext getBeanContext() {
        return $;
    }

    public static <T> T $(Class<T> clazz) {
        return $.get(clazz);
    }

    @SafeVarargs
    public static <T> T $(String name, T... reified) {
        return $.get(name, reified);
    }

    @SafeVarargs
    public static <T> T $(T... reified) {
        return $.get(reified);
    }

    public static void init(String packageName) {
        if ($ != null) return;

        Map<String, Object> beans = new HashMap<>();
        $ = new BeanContext(beans);

        Set<Class<?>> allClasses = findAllClasses(packageName);

        Set<Class<?>> configClasses = new HashSet<>();
        Set<Class<?>> componentClasses = new HashSet<>();
        findAspects(allClasses, configClasses, componentClasses);

        for (Class<?> clazz : componentClasses) {
            Supplier<Object> componentSupplier = CheckedSupplier.unchecked(() -> createFromConstructor(getInjectConstructor(clazz)));
            String componentName = clazz.getName();
            beans.put(componentName, componentSupplier);
        }

        for (Class<?> clazz : configClasses) {
            Supplier<Object> configSupplier = CheckedSupplier.unchecked(() -> createFromConstructor(getInjectConstructor(clazz)));
            String configName = clazz.getName();
            beans.put(configName, configSupplier);

            for (Method method : clazz.getDeclaredMethods()) {
                Bean beanAnnotation = method.getAnnotation(Bean.class);
                if (beanAnnotation != null) {
                    String beanName = beanAnnotation.value();
                    Supplier<Object> beanSupplier = CheckedSupplier.unchecked(() -> createFromMethod(clazz, method));
                    if (beanName == null || beanName.isEmpty()) {
                        beans.put(method.getName(), beanSupplier);
                    } else {
                        beans.put(beanName, beanSupplier);
                    }
                    beans.put(method.getReturnType().getName(), beanSupplier);
                }
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
                .map(parameter -> getBeanContext().get(parameter.getName(), parameter.getType()))
                .toArray(Object[]::new);
        return constructor.newInstance(parameters);
    }

    static Object createFromMethod(Class<?> configClass, Method method) throws Exception {
        Object[] parameters = Stream.of(method.getParameters())
                .map(parameter -> getBeanContext().get(parameter.getName(), parameter.getType()))
                .toArray(Object[]::new);
        return method.invoke(getBeanContext().get(configClass), parameters);
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

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getClassOf(T[] array) {
        return (Class<T>) array.getClass().getComponentType();
    }


    Map<String, Object> beans;

    BeanContext(Map<String, Object> beans) {
        this.beans = beans;
    }

    public <T> T get(Class<T> clazz) {
        Object bean = beans.get(clazz.getName());
        return strip(clazz.getName(), bean, clazz);
    }

    @SafeVarargs
    public final <T> T get(String name, T... reified) {
        Class<T> clazz = getClassOf(reified);
        Object bean = beans.get(name);
        if (bean != null) {
            return strip(name, bean, clazz);
        }
        bean = beans.get(clazz.getName());
        return strip(clazz.getName(), bean, clazz);
    }


    @SafeVarargs
    public final <T> T get(T... reified) {
        Class<T> clazz = getClassOf(reified);
        Object bean = beans.get(clazz.getName());
        return strip(clazz.getName(), bean, clazz);
    }

    private <T> T strip(String name, Object object, Class<T> clazz) {
        if (object instanceof Supplier<?> supplier) {
            T stripped = strip(name, supplier.get(), clazz);
            beans.put(name, stripped);
            return stripped;
        }
        return clazz.cast(object);
    }
}
