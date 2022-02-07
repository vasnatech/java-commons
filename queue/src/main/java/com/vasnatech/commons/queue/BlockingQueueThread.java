package com.vasnatech.commons.queue;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BlockingQueueThread<T> {

    final BlockingQueue<T> queue;
    final int queueCapacity;
    final Duration pollDuration;
    final Duration offerDuration;
    final Thread thread;

    final Consumer<T> dataHandler;
    final Consumer<Exception> exceptionHandler;

    Status status;

    public BlockingQueueThread(
        int queueCapacity,
        Duration pollDuration,
        Duration offerDuration,
        Consumer<T> dataHandler,
        Consumer<Exception> exceptionHandler
    ) {
        this.queue = new LinkedBlockingQueue<>(queueCapacity);
        this.queueCapacity = queueCapacity;
        this.pollDuration = pollDuration;
        this.offerDuration = offerDuration;
        this.dataHandler = dataHandler;
        this.exceptionHandler = exceptionHandler;
        this.thread = new Thread(this::execute);
        this.status = Status.Running;
        thread.start();
    }

    public void interrupt() {
        status = Status.ShutDown;
        synchronized (thread) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                handle(e);
            }
        }
    }

    public void stop() {
        status = Status.ShuttingDown;
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void offer(T data) {
        try {
            if (status != Status.Running)
                throw new IllegalStateException("Queue is not running.");
            if (!queue.offer(data, offerDuration.toNanos(), TimeUnit.NANOSECONDS))
                throw new IllegalStateException("Queue out of capacity. Capacity: " + queueCapacity);
        } catch (Exception e) {
            handle(e);
        }
    }

    void execute() {
        while (status != Status.ShutDown) {
            try {
                T data = queue.poll(pollDuration.toNanos(), TimeUnit.NANOSECONDS);
                if (data != null) {
                    dataHandler.accept(data);
                }
            } catch (Exception e) {
                handle(e);
            } finally {
                if (status == Status.ShuttingDown)
                    status = Status.ShutDown;
            }
        }
    }

    void handle(Exception e) {
        try {
            exceptionHandler.accept(e);
        } catch (Exception ignored) {
        }
    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    public static <T> Builder<T> builder(Class<T> dataClass) {
        return new Builder<T>();
    }

    public static class Builder<T> {
        int queueCapacity = 1000;
        Duration pollDuration = Duration.ofMillis(1000);
        Duration offerDuration = Duration.ofMillis(1000);
        Consumer<T> dataHandler = System.out::println;
        Consumer<Exception> exceptionHandler = System.out::println;

        public Builder<T> queueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
            return this;
        }

        public  Builder<T> pollDuration(Duration pollDuration) {
            this.pollDuration = pollDuration;
            return this;
        }

        public  Builder<T> offerDuration(Duration offerDuration) {
            this.offerDuration = offerDuration;
            return this;
        }

        public  Builder<T> dataHandler(Consumer<T> dataHandler) {
            this.dataHandler = dataHandler;
            return this;
        }

        public  Builder<T> exceptionHandler(Consumer<Exception> exceptionHandler) {
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        public BlockingQueueThread<T> build() {
            return new BlockingQueueThread<T>(queueCapacity, pollDuration, offerDuration, dataHandler, exceptionHandler);
        }
    }
    
    enum Status {
        Running,
        ShuttingDown,
        ShutDown
    }
}
