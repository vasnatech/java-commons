package com.vasnatech.commons.http;

import java.util.Map;

public interface SyncHttpClient<REQ, RES> extends HttpClient<REQ, RES> {

    RES invoke(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException;

    default SyncCaller<REQ, RES> caller() {
        return new SyncCaller<>(this);
    }

    class SyncCaller<REQ, RES> extends Caller<REQ, RES, SyncCaller<REQ, RES>> {
        final SyncHttpClient<REQ, RES> syncHttpClient;

        public SyncCaller(SyncHttpClient<REQ, RES> syncHttpClient) {
            this.syncHttpClient = syncHttpClient;
        }

        public RES invoke() {
            ensureParametersAndHeaders();
            return syncHttpClient.invoke(requestBody, parameters, requestHeaders);
        }
    }
}
