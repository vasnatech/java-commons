package com.vasnatech.commons.event;

import java.util.function.Consumer;

public interface Listener<E> extends Consumer<E> {

    void happened(E event);

    @Override
    default void accept(E event) {
        happened(event);
    }
}
