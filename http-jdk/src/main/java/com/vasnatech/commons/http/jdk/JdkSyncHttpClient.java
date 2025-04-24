package com.vasnatech.commons.http.jdk;

import com.vasnatech.commons.http.HttpClientException;
import com.vasnatech.commons.http.HttpEndpoint;
import com.vasnatech.commons.http.SyncHttpClient;
import com.vasnatech.commons.resource.Resources;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

public class JdkSyncHttpClient<REQ, RES> extends JdkHttpClient<REQ, RES> implements SyncHttpClient<REQ, RES> {

    public JdkSyncHttpClient(HttpClient httpClient, HttpEndpoint<REQ, RES> endpoint) {
        super(httpClient, endpoint);
    }


    @Override
    public RES invoke(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException {
        return switch (endpoint.method) {
            case GET -> get(parameters, requestHeaders);
            case POST -> post(requestBody, parameters, requestHeaders);
            case PUT -> put(requestBody, parameters, requestHeaders);
            case DELETE -> delete(parameters, requestHeaders);
            default -> throw new HttpClientException(0, "Unsupported http method " + endpoint.method);
        };
    }

    RES get(Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException {
        return response(request(parameters, requestHeaders).GET());
    }

    RES post(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException {
        HttpRequest.BodyPublisher bodyPublisher = requestBody == null
                ? BodyPublishers.noBody()
                : BodyPublishers.ofInputStream(requestBodyInputStream(requestBody));
        return response(request(parameters, requestHeaders).POST(bodyPublisher));
    }

    RES put(REQ requestBody, Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException {
        HttpRequest.BodyPublisher bodyPublisher = requestBody == null
                ? BodyPublishers.noBody()
                : BodyPublishers.ofInputStream(requestBodyInputStream(requestBody));
        return response(request(parameters, requestHeaders).PUT(bodyPublisher));
    }

    RES delete(Map<String, ?> parameters, Map<String, String> requestHeaders) throws HttpClientException {
        return response(request(parameters, requestHeaders).DELETE());
    }

    RES response(HttpRequest.Builder requestBuilder) {
        try {
            HttpResponse<InputStream> httpResponse = httpClient.send(requestBuilder.build(), BodyHandlers.ofInputStream());
            int statusCodeGroup = httpResponse.statusCode() / 100;
            if (statusCodeGroup == 2) {
                return endpoint.deserializer.apply(httpResponse.body());
            } else {
                throw new HttpClientException(httpResponse.statusCode(), Resources.asString(httpResponse.body()));
            }
        } catch (Exception e) {
            throw new HttpClientException(0, e);
        }
    }
}
