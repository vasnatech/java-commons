package com.vasnatech.commons.http;

import com.vasnatech.commons.type.tuple.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SyncHttpClientTest {

    static Random RANDOM = new Random();

    static String randomString() {
        return RandomStringUtils.random(RANDOM.nextInt(20) + 20);
    }

    static String randomRequestBody() {
        return randomString();
    }

    static Pair<String, String> randomPair() {
        return Pair.of(randomString(), randomString());
    }

    static List<Pair<String, String>> randomPairs() {
        return IntStream.range(0, RANDOM.nextInt(20))
                .mapToObj(it -> randomPair())
                .collect(Collectors.toList());
    }

    static Map<String, Object> randomParameters() {
        return IntStream.range(0, RANDOM.nextInt(20))
                .mapToObj(it -> randomPair())
                .collect(Collectors.toMap(Pair::first, Pair::second));
    }

    static Map<String, String> randomRequestHeaders() {
        return IntStream.range(0, RANDOM.nextInt(20))
                .mapToObj(it -> randomPair())
                .collect(Collectors.toMap(Pair::first, Pair::second));
    }

    @Test
    void get_01() {
        String expectedResponse = randomString();
        Map<String, Object> parameters = randomParameters();
        Map<String, String> requestHeaders = randomRequestHeaders();

        SyncHttpClient<String, String> httpClient = mock(SyncHttpClient.class);
        when(httpClient.caller()).thenCallRealMethod();
        when(httpClient.invoke(any(), anyMap(), anyMap())).thenReturn(expectedResponse);

        String actualResponse = httpClient.caller()
                .parameters(parameters)
                .requestHeaders(requestHeaders)
                .invoke();

        assertThat(actualResponse).isEqualTo(expectedResponse);
        ArgumentCaptor<Map<String, Object>> parametersCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String>> requestHeadersCaptor = ArgumentCaptor.forClass(Map.class);
        verify(httpClient).invoke(eq(null), parametersCaptor.capture(), requestHeadersCaptor.capture());
        assertThat(parametersCaptor.getValue()).containsExactlyEntriesOf(parameters);
        assertThat(requestHeadersCaptor.getValue()).containsExactlyEntriesOf(requestHeaders);
    }

    @Test
    void get_02() {
        String expectedResponse = randomString();
        List<Pair<String, String>> parameters = randomPairs();
        List<Pair<String, String>> requestHeaders = randomPairs();

        SyncHttpClient<String, String> httpClient = mock(SyncHttpClient.class);
        when(httpClient.caller()).thenCallRealMethod();
        when(httpClient.invoke(any(), anyMap(), anyMap())).thenReturn(expectedResponse);

        SyncHttpClient.SyncCaller<String, String> caller = httpClient.caller();
        parameters.forEach(Pair.toConsumer(caller::parameter));
        requestHeaders.forEach(Pair.toConsumer(caller::requestHeader));
        String actualResponse = caller.invoke();

        assertThat(actualResponse).isEqualTo(expectedResponse);
        ArgumentCaptor<Map<String, Object>> parametersCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String>> requestHeadersCaptor = ArgumentCaptor.forClass(Map.class);
        verify(httpClient).invoke(eq(null), parametersCaptor.capture(), requestHeadersCaptor.capture());
        assertThat(parametersCaptor.getValue()).contains(parameters.toArray(new Map.Entry[0]));
        assertThat(requestHeadersCaptor.getValue()).contains(requestHeaders.toArray(new Map.Entry[0]));
    }

    @Test
    void post() {
        String expectedResponse = randomString();
        String requestBody = randomRequestBody();
        Map<String, Object> parameters = randomParameters();
        Map<String, String> requestHeaders = randomRequestHeaders();

        SyncHttpClient<String, String> httpClient = mock(SyncHttpClient.class);
        when(httpClient.caller()).thenCallRealMethod();
        when(httpClient.invoke(any(), anyMap(), anyMap())).thenReturn(expectedResponse);

        String actualResponse = httpClient.caller()
                .requestBody(requestBody)
                .parameters(parameters)
                .requestHeaders(requestHeaders)
                .invoke();

        assertThat(actualResponse).isEqualTo(expectedResponse);
        ArgumentCaptor<String> requestBodyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Map<String, Object>> parametersCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String>> requestHeadersCaptor = ArgumentCaptor.forClass(Map.class);
        verify(httpClient).invoke(requestBodyCaptor.capture(), parametersCaptor.capture(), requestHeadersCaptor.capture());
        assertThat(requestBodyCaptor.getValue()).isEqualTo(requestBody);
        assertThat(parametersCaptor.getValue()).containsExactlyEntriesOf(parameters);
        assertThat(requestHeadersCaptor.getValue()).containsExactlyEntriesOf(requestHeaders);
    }

    @Test
    void put() {
        String expectedResponse = randomString();
        String requestBody = randomRequestBody();
        Map<String, Object> parameters = randomParameters();
        Map<String, String> requestHeaders = randomRequestHeaders();

        SyncHttpClient<String, String> httpClient = mock(SyncHttpClient.class);
        when(httpClient.caller()).thenCallRealMethod();
        when(httpClient.invoke(any(), anyMap(), anyMap())).thenReturn(expectedResponse);

        String actualResponse = httpClient.caller()
                .requestBody(requestBody)
                .parameters(parameters)
                .requestHeaders(requestHeaders)
                .invoke();

        assertThat(actualResponse).isEqualTo(expectedResponse);
        ArgumentCaptor<String> requestBodyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Map<String, Object>> parametersCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String>> requestHeadersCaptor = ArgumentCaptor.forClass(Map.class);
        verify(httpClient).invoke(requestBodyCaptor.capture(), parametersCaptor.capture(), requestHeadersCaptor.capture());
        assertThat(requestBodyCaptor.getValue()).isEqualTo(requestBody);
        assertThat(parametersCaptor.getValue()).containsExactlyEntriesOf(parameters);
        assertThat(requestHeadersCaptor.getValue()).containsExactlyEntriesOf(requestHeaders);
    }

    @Test
    void delete() {
        String expectedResponse = randomString();
        Map<String, Object> parameters = randomParameters();
        Map<String, String> requestHeaders = randomRequestHeaders();

        SyncHttpClient<String, String> httpClient = mock(SyncHttpClient.class);
        when(httpClient.caller()).thenCallRealMethod();
        when(httpClient.invoke(any(), anyMap(), anyMap())).thenReturn(expectedResponse);

        String actualResponse = httpClient.caller()
                .parameters(parameters)
                .requestHeaders(requestHeaders)
                .invoke();

        assertThat(actualResponse).isEqualTo(expectedResponse);
        ArgumentCaptor<Map<String, Object>> parametersCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, String>> requestHeadersCaptor = ArgumentCaptor.forClass(Map.class);
        verify(httpClient).invoke(eq(null), parametersCaptor.capture(), requestHeadersCaptor.capture());
        assertThat(parametersCaptor.getValue()).containsExactlyEntriesOf(parameters);
        assertThat(requestHeadersCaptor.getValue()).containsExactlyEntriesOf(requestHeaders);
    }
}
