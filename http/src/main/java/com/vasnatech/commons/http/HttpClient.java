package com.vasnatech.commons.http;

import java.util.HashMap;
import java.util.Map;

public interface HttpClient<REQ, RES>  {

    class Caller<REQ, RES, C extends Caller<REQ, RES, C>> {
        REQ requestBody;
        Map<String, Object> parameters;
        Map<String, String> requestHeaders;

        public C requestBody(REQ requestBody) {
            this.requestBody = requestBody;
            return (C)this;
        }

        public C parameters(Map<String, Object> parameters) {
            this.parameters = parameters;
            return (C)this;
        }
        public C parameter(String key, Object value) {
            if (parameters == null)
                parameters = new HashMap<>();
            this.parameters.put(key, value);
            return (C)this;
        }

        public C requestHeaders(Map<String, String> requestHeaders) {
            this.requestHeaders = requestHeaders;
            return (C)this;
        }
        public C requestHeader(String key, String value) {
            if (requestHeaders == null)
                requestHeaders = new HashMap<>();
            this.requestHeaders.put(key, value);
            return (C)this;
        }

        void ensureParametersAndHeaders() {
            if (parameters == null)
                parameters = Map.of();
            if (requestHeaders == null)
                requestHeaders = Map.of();
        }
    }
}
