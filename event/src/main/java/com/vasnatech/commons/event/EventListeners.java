package com.vasnatech.commons.event;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public final class EventListeners {

    final Consumer<RuntimeException> exceptionHandler;
    final ConcurrentHashMap<Class<?>, ConcurrentLinkedQueue<Listener<?>>> listenersGroupedByEventType;

    public EventListeners() {
        this(Throwable::printStackTrace);
    }
    
    public EventListeners(Consumer<RuntimeException> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        this.listenersGroupedByEventType = new ConcurrentHashMap<>();
    }

    public synchronized <E, L extends Listener<E>> void add(Class<E> eventType, L listener) {
        listenersGroupedByEventType.computeIfAbsent(
                eventType,
                t -> new ConcurrentLinkedQueue<>()
        ).add(listener);
    }

    public synchronized <E, L extends Listener<E>> void remove(Class<E> eventType, L listener) {
        listenersGroupedByEventType.computeIfAbsent(
                eventType,
                t -> new ConcurrentLinkedQueue<>()
        ).remove(listener);
    }

    @SuppressWarnings("unchecked")
    synchronized <E, L extends Listener<E>> ConcurrentLinkedQueue<L> get(Class<E> eventType) {
        Objects.requireNonNull(eventType);
        Class<?> clazz = eventType;
        while (clazz != null) {
            if (listenersGroupedByEventType.containsKey(clazz)) {
                return (ConcurrentLinkedQueue<L>) listenersGroupedByEventType.get(clazz);
            }
            clazz = clazz.getSuperclass();
        }
        ConcurrentLinkedQueue<L> queue = new ConcurrentLinkedQueue<>();
        listenersGroupedByEventType.put(eventType, (ConcurrentLinkedQueue<Listener<?>>) queue);
        return queue;
    }

    @SuppressWarnings("unchecked")
    public <E> void fire(E event) {
        get(event.getClass()).forEach(l -> fire(event, (Listener<E>)l));
    }

    @SuppressWarnings("unchecked")
    public <E> CompletableFuture<Void> fireAsync(E event) {
        return CompletableFuture.allOf(
                get(event.getClass())
                        .stream()
                        .map(l -> CompletableFuture.runAsync(
                                () -> fire(event, (Listener<E>)l)
                        ))
                        .toArray(CompletableFuture<?>[]::new)
        );
    }

    <E, L extends Listener<E>> void fire(E event, L listener) {
        try {
            listener.happened(event);
        } catch (RuntimeException e) {
            try {
                exceptionHandler.accept(e);
            } catch (RuntimeException e1) {
                try {
                    exceptionHandler.accept(e1);
                } catch (RuntimeException e2) {
                    //DO NOTHING. Exception Handler failed.
                }
            }
        }
    }
}
