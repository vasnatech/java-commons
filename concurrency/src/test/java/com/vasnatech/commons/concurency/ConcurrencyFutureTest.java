package com.vasnatech.commons.concurency;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcurrencyFutureTest {

    static Random RANDOM = new Random();

    @Test
    void join_with_list() throws ExecutionException, InterruptedException {
        int size = RANDOM.nextInt(1024);
        List<Integer> expected = new ArrayList<>(size);
        final List<CompletableFuture<Integer>> futureList = IntStream.range(0, size)
                .mapToObj(ignored -> {
                    Integer value = RANDOM.nextInt();
                    expected.add(value);
                    return value;
                })
                .map(value -> CompletableFuture.supplyAsync(() -> value))
                .collect(Collectors.toList());

        CompletableFuture<List<Integer>> future = Futures.join(futureList);
        final List<Integer> actual = future.get();

        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    void join_with_array() throws ExecutionException, InterruptedException {
        int size = RANDOM.nextInt(1024);
        List<Integer> expected = new ArrayList<>(size);
        final CompletableFuture<Integer>[] futures = IntStream.range(0, size)
                .mapToObj(ignored -> {
                    Integer value = RANDOM.nextInt();
                    expected.add(value);
                    return value;
                })
                .map(value -> CompletableFuture.supplyAsync(() -> value))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<List<Integer>> future = Futures.join(futures);
        final List<Integer> actual = future.get();

        assertEquals(expected, actual);
    }
}
