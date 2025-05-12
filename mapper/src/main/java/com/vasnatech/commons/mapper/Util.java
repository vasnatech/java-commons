package com.vasnatech.commons.mapper;

class Util {

    @SuppressWarnings("unchecked")
    static <T> Class<T> classOf(T[] array) {
        return (Class<T>) array.getClass().getComponentType();
    }
}
