package com.vasnatech.commons.http.jdk;

import com.vasnatech.commons.http.HttpClient;
import com.vasnatech.commons.http.HttpClientException;
import com.vasnatech.commons.http.HttpEndpoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.function.Supplier;

public abstract class JdkHttpClient<REQ, RES> implements HttpClient<REQ, RES> {

    final java.net.http.HttpClient httpClient;
    final HttpEndpoint<REQ, RES> endpoint;

    JdkHttpClient(java.net.http.HttpClient httpClient, HttpEndpoint<REQ, RES> endpoint) {
        this.httpClient = httpClient;
        this.endpoint = endpoint;
    }

    HttpRequest.Builder request(Map<String, ?> parameters, Map<String, String> requestHeaders) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.uri(URI.create(endpoint.urlTemplate.toString(parameters)));
        endpoint.requestHeaders.forEach(requestBuilder::setHeader);
        requestHeaders.forEach(requestBuilder::setHeader);
        return requestBuilder;
    }

    Supplier<InputStream> requestBodyInputStream(REQ requestBody) {
        return () -> {
            try {
                PipedInputStream in = new PipedInputStream(4096);
                PipedOutputStream out = new PipedOutputStream(in);
                endpoint.serializer.accept(out, requestBody);
                return in;
            } catch (IOException e) {
                throw new HttpClientException(0, e);
            }
        };
    }
}
