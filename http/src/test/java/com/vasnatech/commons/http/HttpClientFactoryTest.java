package com.vasnatech.commons.http;

import com.vasnatech.commons.type.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class HttpClientFactoryTest {

    static Random RANDOM = new Random();

    static String randomString() {
        return RandomStringUtils.random(RANDOM.nextInt(20) + 20);
    }

    static HttpClientFactory createHttpClientFactory() {
        SyncHttpClient syncHttpClient = mock(SyncHttpClient.class);
        AsyncHttpClient asyncHttpClient = mock(AsyncHttpClient.class);
        HttpClientFactory httpClientFactory = createHttpClientFactory(syncHttpClient, asyncHttpClient);
        return httpClientFactory;
    }

    static HttpClientFactory createHttpClientFactory(SyncHttpClient syncHttpClient, AsyncHttpClient asyncHttpClient) {
        return new HttpClientFactory() {
            public <REQ, RES> SyncHttpClient<REQ, RES> createSync(HttpEndpoint<REQ, RES> httpEndpoint) {
                return syncHttpClient;
            }
            public <REQ, RES> AsyncHttpClient<REQ, RES> createAsync(HttpEndpoint<REQ, RES> httpEndpoint) {
                return asyncHttpClient;
            }
        };
    }

    @Test
    void get_set_Default() {
        HttpClientFactory httpClientFactory = createHttpClientFactory();

        HttpClientFactory.setDefault(httpClientFactory);

        assertThat(HttpClientFactory.getDefault()).isEqualTo(httpClientFactory);
        assertThat(HttpClientFactory.get(null)).isEqualTo(httpClientFactory);
    }

    @Test
    void get_set() {
        List<Pair<String, HttpClientFactory>> httpClientFactories = IntStream.range(0, RANDOM.nextInt(20))
                .mapToObj(it -> Pair.of(randomString(), createHttpClientFactory()))
                .collect(Collectors.toList());
        httpClientFactories.forEach(Pair.toConsumer(HttpClientFactory::set));
        HttpClientFactory defaultHttpClientFactory = createHttpClientFactory();
        HttpClientFactory.set(null, defaultHttpClientFactory);

        SoftAssertions softly = new SoftAssertions();
        httpClientFactories.forEach(pair -> softly.assertThat(HttpClientFactory.get(pair.first())).isEqualTo(pair.second()));
        softly.assertThat(HttpClientFactory.getDefault()).isEqualTo(defaultHttpClientFactory);
        softly.assertAll();
    }

    @Test
    void sync() {
        SyncHttpClient<String, String> syncHttpClient = mock(SyncHttpClient.class);
        HttpClientFactory httpClientFactory = createHttpClientFactory(syncHttpClient, null);
        HttpClientFactory.setDefault(httpClientFactory);
        HttpEndpoint<String, String> httpEndpoint = mock(HttpEndpoint.class);

        SyncHttpClient<String, String> actualSyncHttpClient = HttpClientFactory.sync(httpEndpoint);

        assertThat(actualSyncHttpClient).isEqualTo(syncHttpClient);
    }

    @Test
    void sync_with_key() {
        List<Pair<String, SyncHttpClient>> syncHttpClients = IntStream.range(0, RANDOM.nextInt(20))
                .mapToObj(it -> Pair.of(randomString(), mock(SyncHttpClient.class)))
                .collect(Collectors.toList());
        syncHttpClients.stream()
                .map(Pair.toPairFunctionSecond(syncHttpClient -> createHttpClientFactory(syncHttpClient, null)))
                .forEach(Pair.toConsumer(HttpClientFactory::set));
        HttpEndpoint<String, String> httpEndpoint = mock(HttpEndpoint.class);

        SoftAssertions softly = new SoftAssertions();
        syncHttpClients.stream()
                        .forEach(pair -> {
                            SyncHttpClient<String, String> actualSyncHttpClient = HttpClientFactory.sync(pair.first(), httpEndpoint);

                            assertThat(actualSyncHttpClient).isEqualTo(pair.second());
                        });
        softly.assertAll();
    }
}
