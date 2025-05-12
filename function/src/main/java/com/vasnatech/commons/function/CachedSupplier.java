package com.vasnatech.commons.function;

import java.util.function.Supplier;

@FunctionalInterface
public interface CachedSupplier<T> extends Supplier<T> {

    T get();

    static <T> CachedSupplier<T> of(Supplier<T> source) {
        return new Impl<>(source);
    }

    class Impl<T> implements CachedSupplier<T> {

        final Supplier<T> source;
        T cache = null;
        boolean isCached = false;

        public Impl(Supplier<T> source) {
            this.source = source;
        }

        @Override
        public T get() {
            if (!isCached) {
                cache = source.get();
                isCached = true;
            }
            return cache;
        }
    }
}
