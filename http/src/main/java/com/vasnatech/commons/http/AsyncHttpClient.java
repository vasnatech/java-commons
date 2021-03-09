package com.vasnatech.commons.http;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AsyncHttpClient<REQ, RES> {

    CompletableFuture<RES> get(Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    CompletableFuture<RES> post(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    CompletableFuture<RES> put(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    CompletableFuture<RES> delete(Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    default ASyncCaller<REQ, RES> caller() {
        return new ASyncCaller<>(this);
    }

    class ASyncCaller<REQ, RES> {
        final AsyncHttpClient<REQ, RES> asyncHttpClient;
        REQ requestBody;
        Map<String, Object> parameters;
        Map<String, String> requestHeaders;

        public ASyncCaller(AsyncHttpClient<REQ, RES> asyncHttpClient) {
            this.asyncHttpClient = asyncHttpClient;
        }

        public ASyncCaller<REQ, RES> requestBody(REQ requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public ASyncCaller<REQ, RES> parameters(Map<String, Object> parameters) {
            this.parameters = parameters;
            return this;
        }
        public ASyncCaller<REQ, RES> parameter(String key, Object value) {
            if (parameters == null)
                parameters = new HashMap<>();
            this.parameters.put(key, value);
            return this;
        }

        public ASyncCaller<REQ, RES> requestHeaders(Map<String, String> requestHeaders) {
            this.requestHeaders = requestHeaders;
            return this;
        }
        public ASyncCaller<REQ, RES> requestHeader(String key, String value) {
            if (requestHeaders == null)
                requestHeaders = new HashMap<>();
            this.requestHeaders.put(key, value);
            return this;
        }

        void ensureParametersAndHeaders() {
            if (parameters == null)
                parameters = Map.of();
            if (requestHeaders == null)
                requestHeaders = Map.of();
        }

        public CompletableFuture<RES> get() {
            ensureParametersAndHeaders();
            return asyncHttpClient.get(parameters, requestHeaders);
        }

        public CompletableFuture<RES> post() {
            ensureParametersAndHeaders();
            return asyncHttpClient.post(requestBody, parameters, requestHeaders);
        }

        public CompletableFuture<RES> put() {
            ensureParametersAndHeaders();
            return asyncHttpClient.put(requestBody, parameters, requestHeaders);
        }

        public CompletableFuture<RES> delete() {
            ensureParametersAndHeaders();
            return asyncHttpClient.delete(parameters, requestHeaders);
        }
    }
}
