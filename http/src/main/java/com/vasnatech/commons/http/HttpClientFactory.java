package com.vasnatech.commons.http;

import java.util.HashMap;
import java.util.Map;

public interface HttpClientFactory {

    Map<String, HttpClientFactory> FACTORIES = new HashMap<>();

    static HttpClientFactory getDefault() {
        return FACTORIES.get(null);
    }

    static void setDefault(HttpClientFactory factory) {
        FACTORIES.put(null, factory);
    }

    static HttpClientFactory get(String key) {
        return FACTORIES.getOrDefault(key, getDefault());
    }

    static void set(String key, HttpClientFactory factory) {
        FACTORIES.put(key, factory);
    }


    static <REQ, RES> SyncHttpClient<REQ, RES> sync(HttpEndpoint<REQ, RES> httpEndpoint) {
        return sync(null, httpEndpoint);
    }

    static <REQ, RES> SyncHttpClient<REQ, RES> sync(String key, HttpEndpoint<REQ, RES> httpEndpoint) {
        HttpClientFactory httpClientFactory = get(key);
        if (httpClientFactory == null)
            throw new IllegalArgumentException("Unable to find http client factory for " + key);
        return httpClientFactory.createSync(httpEndpoint);
    }

    static <REQ, RES> AsyncHttpClient<REQ, RES> async(HttpEndpoint<REQ, RES> httpEndpoint) {
        return async(null, httpEndpoint);
    }

    static <REQ, RES> AsyncHttpClient<REQ, RES> async(String key, HttpEndpoint<REQ, RES> httpEndpoint) {
        HttpClientFactory httpClientFactory = get(key);
        if (httpClientFactory == null)
            throw new IllegalArgumentException("Unable to find http client factory for " + key);
        return httpClientFactory.createAsync(httpEndpoint);
    }


    default <REQ, RES> SyncHttpClient<REQ, RES> createSync(HttpEndpoint<REQ, RES> httpEndpoint) {
        throw new UnsupportedOperationException("HttpClient sync access is not supported for " + httpEndpoint.name);
    }
    default <REQ, RES> AsyncHttpClient<REQ, RES> createAsync(HttpEndpoint<REQ, RES> httpEndpoint) {
        throw new UnsupportedOperationException("HttpClient async access is not supported for " + httpEndpoint.name);
    }
}