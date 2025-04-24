package com.vasnatech.commons.http;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AsyncHttpClient<REQ, RES> extends HttpClient<REQ, RES> {

    CompletableFuture<RES> invoke(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    default ASyncCaller<REQ, RES> caller() {
        return new ASyncCaller<>(this);
    }

    class ASyncCaller<REQ, RES> extends Caller<REQ, RES, ASyncCaller<REQ, RES>> {
        final AsyncHttpClient<REQ, RES> asyncHttpClient;

        public ASyncCaller(AsyncHttpClient<REQ, RES> asyncHttpClient) {
            this.asyncHttpClient = asyncHttpClient;
        }

        public CompletableFuture<RES> invoke() {
            ensureParametersAndHeaders();
            return asyncHttpClient.invoke(requestBody, parameters, requestHeaders);
        }
    }
}
