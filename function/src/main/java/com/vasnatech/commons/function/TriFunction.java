package com.vasnatech.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<T, U, V, R> {

    R apply(T t, U u, V v);

    default <S> TriFunction<T, U, V, S> andThen(Function<? super R, ? extends S> after) {
        Objects.requireNonNull(after);
        return (t, u, v) -> after.apply(apply(t, u, v));
    }

    default <T$, U$, V$> TriFunction<T$, U$, V$, R> compose(
            Function<? super T$, ? extends T> tFunction,
            Function<? super U$, ? extends U> uFunction,
            Function<? super V$, ? extends V> vFunction
    ) {
        return (t, u, v) -> apply(tFunction.apply(t), uFunction.apply(u), vFunction.apply(v));
    }
}
