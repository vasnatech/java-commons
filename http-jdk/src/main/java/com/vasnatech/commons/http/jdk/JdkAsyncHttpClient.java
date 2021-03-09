package com.vasnatech.commons.http.jdk;

import com.vasnatech.commons.http.AsyncHttpClient;
import com.vasnatech.commons.http.HttpClientException;
import com.vasnatech.commons.http.HttpEndpoint;
import com.vasnatech.commons.resource.Resources;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class JdkAsyncHttpClient<REQ, RES> extends JdkHttpClient<REQ, RES> implements AsyncHttpClient<REQ, RES> {

    public JdkAsyncHttpClient(HttpClient httpClient, HttpEndpoint<REQ, RES> endpoint) {
        super(httpClient, endpoint);
    }

    @Override
    public CompletableFuture<RES> get(Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException {
        return response(request(parameters, requestHeaders).GET());
    }

    @Override
    public CompletableFuture<RES> post(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException {
        return response(
                request(parameters, requestHeaders)
                        .POST(BodyPublishers.ofInputStream(requestBodyInputStream(requestBody)))
        );
    }

    @Override
    public CompletableFuture<RES> put(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException {
        return response(
                request(parameters, requestHeaders)
                        .PUT(BodyPublishers.ofInputStream(requestBodyInputStream(requestBody)))
        );
    }

    @Override
    public CompletableFuture<RES> delete(Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException {
        return response(request(parameters, requestHeaders).DELETE());
    }

    CompletableFuture<RES> response(HttpRequest.Builder requestBuilder) {
        try {
            return httpClient
                    .sendAsync(requestBuilder.build(), BodyHandlers.ofInputStream())
                    .thenApply(httpResponse -> {
                        int statusCodeGroup = httpResponse.statusCode() / 100;
                        if (statusCodeGroup == 2) {
                            return endpoint.deserializer.apply(httpResponse.body());
                        } else {
                            try {
                                throw new HttpClientException(httpResponse.statusCode(), Resources.asString(httpResponse.body()));
                            } catch (IOException e) {
                                throw new HttpClientException(httpResponse.statusCode());
                            }
                        }
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(new HttpClientException(0, e));
        }
    }
}
