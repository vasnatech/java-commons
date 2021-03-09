package com.vasnatech.commons.http;

import java.util.HashMap;
import java.util.Map;

public interface SyncHttpClient<REQ, RES> {

    RES get(Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    RES post(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    RES put(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    RES delete(Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    default SyncCaller<REQ, RES> caller() {
        return new SyncCaller<>(this);
    }

    class SyncCaller<REQ, RES> {
        final SyncHttpClient<REQ, RES> syncHttpClient;
        REQ requestBody;
        Map<String, Object> parameters;
        Map<String, String> requestHeaders;

        public SyncCaller(SyncHttpClient<REQ, RES> syncHttpClient) {
            this.syncHttpClient = syncHttpClient;
        }

        public SyncCaller<REQ, RES> requestBody(REQ requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public SyncCaller<REQ, RES> parameters(Map<String, Object> parameters) {
            this.parameters = parameters;
            return this;
        }
        public SyncCaller<REQ, RES> parameter(String key, Object value) {
            if (parameters == null)
                parameters = new HashMap<>();
            this.parameters.put(key, value);
            return this;
        }

        public SyncCaller<REQ, RES> requestHeaders(Map<String, String> requestHeaders) {
            this.requestHeaders = requestHeaders;
            return this;
        }
        public SyncCaller<REQ, RES> requestHeader(String key, String value) {
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

        public RES get() {
            ensureParametersAndHeaders();
            return syncHttpClient.get(parameters, requestHeaders);
        }

        public RES post() {
            ensureParametersAndHeaders();
            return syncHttpClient.post(requestBody, parameters, requestHeaders);
        }

        public RES put() {
            ensureParametersAndHeaders();
            return syncHttpClient.put(requestBody, parameters, requestHeaders);
        }

        public RES delete() {
            ensureParametersAndHeaders();
            return syncHttpClient.delete(parameters, requestHeaders);
        }
    }
}
