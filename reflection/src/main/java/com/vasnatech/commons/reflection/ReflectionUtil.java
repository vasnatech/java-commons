package com.vasnatech.commons.reflection;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ReflectionUtil {

    private ReflectionUtil() {
    }

    static Class<?>[] EMPTY_PARAMETER_CLASSES = new Class<?>[0];
    static Object[] EMPTY_PARAMETERS = new Object[0];

    public static Object getProperty(Object parent, String name) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (parent == null || StringUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = parent.getClass();
        try {
            return field(clazz, parent, name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return getter(clazz, parent, name);
        }
    }

    public static void setProperty(Object parent, String name, Object value) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (parent == null || StringUtils.isEmpty(name)) {
            return;
        }
        try {
            field(parent, name, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            setter(parent, name, value);
        }
    }

    public static Object field(Object object, String name) throws NoSuchFieldException, IllegalAccessException {
        if (object == null || StringUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = object.getClass();
        return field(clazz, object, name);
    }

    public static Object field(Class<?> clazz, Object object, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getField(name);
        return field.get(object);
    }

    public static void field(Object object, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        if (object == null || StringUtils.isEmpty(name)) {
            return;
        }
        Class<?> clazz = object.getClass();
        field(clazz, object, name, value);
    }

    public static void field(Class<?> clazz, Object object, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getField(name);
        field.set(object, value);
    }

    public static Object getter(Object object, String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (object == null || StringUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = object.getClass();
        return getter(clazz, object, name);
    }

    public static Object getter(Class<?> clazz, Object object, String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getterName;
        if (!Character.isUpperCase(name.charAt(0))) {
            getterName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        } else {
            getterName = name;
        }
        getterName = "get" + getterName;
        return invokeExactMethod(clazz, object, getterName, EMPTY_PARAMETER_CLASSES, EMPTY_PARAMETERS);
    }

    public static void setter(Object object, String name, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (object == null || StringUtils.isEmpty(name)) {
            return;
        }
        Class<?> clazz = object.getClass();
        Class<?> valueClass = value == null ? Object.class : value.getClass();
        setter(clazz, object, name, valueClass, value);
    }

    public static void setter(Class<?> clazz, Object object, String name, Class<?> valueClass, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String setterName;
        if (!Character.isUpperCase(name.charAt(0))) {
            setterName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        } else {
            setterName = name;
        }
        setterName = "set" + setterName;
        Object result = invokeMethod(clazz, object, setterName, new Class<?>[]{valueClass}, new Object[]{value});

    }

    public static Object invokeMethod(Object object, String name, Object... parameters) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (object == null || StringUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = object.getClass();
        Class<?>[] parameterClasses = Stream.of(parameters).map(p -> p == null ? null : p.getClass()).toArray(Class<?>[]::new);
        return invokeMethod(clazz, object, name, parameterClasses, parameters);
    }

    public static Object invokeMethod(Class<?> clazz, Object parent, String name, Class<?>[] parameterClasses, Object[] parameters) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        try {
            return ReflectionUtil.invokeExactMethod(clazz, parent, name, parameterClasses, parameters);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            try {
                Object result = ReflectionUtil.invokeInterfaceMethod(clazz, parent, name, parameterClasses, parameters);
                if (result != null) {
                    return result;
                } else {
                    return ReflectionUtil.invokeBestMethod(clazz, parent, name, parameterClasses, parameters);
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e2) {
                throw e;
            }
        }
    }

    public static Object invokeExactMethod(Object object, String name, Object... parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (object == null || StringUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = object.getClass();
        Class<?>[] parameterClasses = Stream.of(parameters).map(p -> p == null ? null : p.getClass()).toArray(Class<?>[]::new);
        return invokeExactMethod(clazz, object, name, parameterClasses, parameters);
    }

    public static Object invokeExactMethod(Class<?> clazz, Object object, String name, Class<?>[] parameterClasses, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = clazz.getMethod(name, parameterClasses);
        return method.invoke(object, parameters);
    }

    public static Object invokeBestMethod(Object object, String name, Object... parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (object == null || StringUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = object.getClass();
        Class<?>[] parameterClasses = Stream.of(parameters).map(p -> p == null ? null : p.getClass()).toArray(Class<?>[]::new);
        return invokeBestMethod(clazz, object, name, parameterClasses, parameters);
    }

    public static Object invokeBestMethod(Class<?> clazz, Object object, String name, Class<?>[] parameterClasses, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Method method : clazz.getMethods()) {
            if (method.getParameterCount() != parameterClasses.length) {
                continue;
            }
            if (!method.getName().equals(name)) {
                continue;
            }
            boolean match = true;
            Parameter[] methodParameters = method.getParameters();
            for (int parameterIndex = 0; match && parameterIndex < method.getParameterCount(); ++parameterIndex) {
                if (parameterClasses[parameterIndex] == null) {
                    continue;
                }
                match = methodParameters[parameterIndex].getType().isAssignableFrom(parameterClasses[parameterIndex]);
            }
            if (match) {
                return method.invoke(object, parameters);
            }
        }
        return null;
    }

    public static Object invokeInterfaceMethod(Object object, String name, Object... parameters) {
        if (object == null || StringUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = object.getClass();
        Class<?>[] parameterClasses = Stream.of(parameters).map(p -> p == null ? null : p.getClass()).toArray(Class<?>[]::new);
        return invokeInterfaceMethod(clazz, object, name, parameterClasses, parameters);

    }

    private static Object invokeInterfaceMethod(Class<?> clazz, Object parent, String name, Class<?>[] parameterClasses, Object[] parameters) {
        Class<?> classIterator = clazz;
        while (classIterator != null) {
            for (Class<?> classInterface : classIterator.getInterfaces()) {
                try {
                    Method method = classInterface.getMethod(name, parameterClasses);
                    return method.invoke(parent, parameters);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore) {
                }
            }
            classIterator = classIterator.getSuperclass();
        }
        return null;
    }

    public static Object invokeStaticMethod(Class<?> clazz, String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeStaticMethod(clazz, name, new Class[0], new Object[0]);
    }

    public static Object invokeStaticMethod(Class<?> clazz, String name, Class<?>[] parameterClasses, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (clazz == null || StringUtils.isEmpty(name)) {
            return null;
        }
        Method method = clazz.getMethod(name, parameterClasses);
        return method.invoke(null, parameters);
    }

    public static ClassLoader createClassLoaderFromUrls(URL... urls) {
        return new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
    }

    public static ClassLoader createClassLoaderFromUrls(Collection<URL> urls) {
        return createClassLoaderFromUrls(urls.toArray(URL[]::new));
    }

    public static ClassLoader createClassLoader(Set<String> classPaths) throws IOException {
        URL[] urls = new URL[classPaths.size()];
        int index = 0;
        for (String classPath : classPaths) {
            urls[index++] = Path.of(classPath).toUri().toURL();
        }
        return createClassLoaderFromUrls(urls);
    }

    public static ClassLoader createClassLoader(Collection<String> classPaths) throws IOException {
        return createClassLoader(new HashSet<>(classPaths));
    }

    public static ClassLoader createClassLoader(String... classPaths) throws IOException {
        return createClassLoader(Stream.of(classPaths).collect(Collectors.toSet()));
    }
}
