package com.vasnatech.commons.event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.stream.Stream;

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

    public <E, L extends Listener<E>> void add(Class<E> eventType, L listener) {
        listenersGroupedByEventType.computeIfAbsent(
                eventType,
                t -> new ConcurrentLinkedQueue<>()
        ).add(listener);
    }

    public <E, L extends Listener<E>> void remove(Class<E> eventType, L listener) {
        listenersGroupedByEventType.computeIfAbsent(
                eventType,
                t -> new ConcurrentLinkedQueue<>()
        ).remove(listener);
    }

    @SuppressWarnings("unchecked")
    public <E> void fire(E event) {
        listenersGroupedByEventType.computeIfAbsent(
                event.getClass(),
                t -> new ConcurrentLinkedQueue<>()
        ).forEach(l -> fire(event, (Listener<E>)l));
    }

    @SuppressWarnings("unchecked")
    public <E> CompletableFuture<Void> fireAsync(E event) {
        ConcurrentLinkedQueue<Listener<?>> listeners = listenersGroupedByEventType.computeIfAbsent(
                event.getClass(),
                t -> new ConcurrentLinkedQueue<>()
        );
        Stream<CompletableFuture<Void>> stream = listeners.stream()
                .map(l -> CompletableFuture.runAsync(
                        () -> fire(event, (Listener<E>)l)
                ));
        return CompletableFuture.allOf(stream.toArray(CompletableFuture<?>[]::new));
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
                }
            }
        }
    }
}
