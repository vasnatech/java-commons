package com.vasnatech.commons.concurency;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Futures {

    static <T> CompletableFuture<List<T>> join(List<CompletableFuture<T>> futureList) {
        return CompletableFuture
                .allOf(futureList.toArray(new CompletableFuture<?>[0]))
                .thenApply(ignore -> futureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
                );
    }

    static <T> CompletableFuture<Stream<T>> join(Stream<CompletableFuture<T>> futureStream) {
        return CompletableFuture
                .allOf(futureStream.toArray(CompletableFuture<?>[]::new))
                .thenApply(ignore -> futureStream.map(CompletableFuture::join));
    }

    static CompletableFuture<Void> joinVoid(List<CompletableFuture<Void>> futureList) {
        return CompletableFuture
                .allOf(futureList.toArray(new CompletableFuture<?>[0]))
                .thenApply(ignored -> null);
    }

    static CompletableFuture<Void> joinVoid(Stream<CompletableFuture<Void>> futureList) {
        return CompletableFuture.allOf(futureList.toArray(CompletableFuture<?>[]::new));
    }

    @SafeVarargs
    static <T> CompletableFuture<List<T>> join(CompletableFuture<T>... futures) {
        return CompletableFuture
                .allOf(futures)
                .thenApply(ignore -> Stream.of(futures)
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
                );
    }

    @SafeVarargs
    static CompletableFuture<Void> joinVoid(CompletableFuture<Void>... futures) {
        return CompletableFuture
                .allOf(futures)
                .thenApply(ignored -> null);
    }
}
