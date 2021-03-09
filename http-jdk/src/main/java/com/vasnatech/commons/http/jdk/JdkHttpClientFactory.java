package com.vasnatech.commons.http.jdk;

import com.vasnatech.commons.http.AsyncHttpClient;
import com.vasnatech.commons.http.HttpClientFactory;
import com.vasnatech.commons.http.HttpEndpoint;

import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;

public class JdkHttpClientFactory implements HttpClientFactory {

    final HttpClient httpClient;

    public JdkHttpClientFactory() {
        this(HttpClient.newHttpClient());
    }

    public JdkHttpClientFactory(int httpVersion) {
        this(HttpClient.newBuilder().version(httpVersion == 2 ? Version.HTTP_2 : Version.HTTP_1_1).build());
    }

    public JdkHttpClientFactory(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public <REQ, RES> JdkSyncHttpClient<REQ, RES> createSync(HttpEndpoint<REQ, RES> httpEndpoint) {
        return new JdkSyncHttpClient<>(httpClient, httpEndpoint);
    }

    @Override
    public <REQ, RES> AsyncHttpClient<REQ, RES> createAsync(HttpEndpoint<REQ, RES> httpEndpoint) {
        return new JdkAsyncHttpClient<>(httpClient, httpEndpoint);
    }
}