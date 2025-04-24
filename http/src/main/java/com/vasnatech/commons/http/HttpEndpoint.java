package com.vasnatech.commons.http;

import com.vasnatech.commons.serialize.Deserializer;
import com.vasnatech.commons.serialize.Serializer;
import com.vasnatech.commons.text.ParametrizedString;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class HttpEndpoint<REQ, RES> {

    public final Class<REQ> requestBodyType;
    public final Class<RES> responseBodyType;
    public final BiConsumer<OutputStream, REQ> serializer;
    public final Function<InputStream, RES> deserializer;
    public final String group;
    public final String name;
    public final HttpMethod method;
    public final ParametrizedString urlTemplate;
    public final Map<String, String> requestHeaders;

    public HttpEndpoint(
            Class<REQ> requestBodyType,
            Class<RES> responseBodyType,
            BiConsumer<OutputStream, REQ> serializer,
            Function<InputStream, RES> deserializer,
            String group,
            String name,
            HttpMethod method,
            ParametrizedString urlTemplate,
            Map<String, String> requestHeaders
    ) {
        this.requestBodyType = requestBodyType;
        this.responseBodyType = responseBodyType;
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.group = group;
        this.name = name;
        this.method = method;
        this.urlTemplate = urlTemplate;
        this.requestHeaders = requestHeaders;
    }

    public HttpEndpoint(
            Class<REQ> requestBodyType,
            Class<RES> responseBodyType,
            Serializer serializer,
            Deserializer deserializer,
            String group,
            String name,
            HttpMethod method,
            ParametrizedString urlTemplate,
            Map<String, String> requestHeaders
    ) {
        this.requestBodyType = requestBodyType;
        this.responseBodyType = responseBodyType;
        this.serializer = toConsumer(serializer);
        this.deserializer = toFunction(deserializer, responseBodyType);
        this.group = group;
        this.name = name;
        this.method = method;
        this.urlTemplate = urlTemplate;
        this.requestHeaders = requestHeaders;
    }

    public SyncHttpClient.SyncCaller<REQ, RES> sync() {
        return sync(null);
    }

    public SyncHttpClient.SyncCaller<REQ, RES> sync(String key) {
        return HttpClientFactory.sync(key, this).caller();
    }

    public AsyncHttpClient.ASyncCaller<REQ, RES> async() {
        return async(null);
    }

    public AsyncHttpClient.ASyncCaller<REQ, RES> async(String key) {
        return HttpClientFactory.async(key, this).caller();
    }


    public static <REQ, RES> Builder<REQ, RES> builder(Class<REQ> requestBodyType, Class<RES> responseBodyType) {
        return new Builder<>(requestBodyType, responseBodyType);
    }

    public static <RES> Builder<Void, RES> builder(Class<RES> responseBodyType) {
        return new Builder<>(Void.class, responseBodyType)
                .serializer(getDoNothingSerializer());
    }

    public static Builder<Void, Void> builder() {
        return new Builder<>(Void.class, Void.class)
                .serializer(getDoNothingSerializer())
                .deserializer(getDoNothingDeserializer());
    }

    public static class Builder<REQ, RES> {
        final Class<REQ> requestBodyType;
        final Class<RES> responseBodyType;
        BiConsumer<OutputStream, REQ> serializer;
        Function<InputStream, RES> deserializer;
        String group;
        String name;
        HttpMethod method;
        ParametrizedString urlTemplate;
        Map<String, String> requestHeaders;

        Builder(Class<REQ> requestBodyType, Class<RES> responseBodyType) {
            this.requestBodyType = requestBodyType;
            this.responseBodyType = responseBodyType;
        }

        public Builder<REQ, RES>  serializer(BiConsumer<OutputStream, REQ> serializer) {
            this.serializer = serializer;
            return this;
        }

        public Builder<REQ, RES>  serializer(Serializer serializer) {
            this.serializer = toConsumer(serializer);
            return this;
        }

        public Builder<REQ, RES>  deserializer(Function<InputStream, RES> deserializer) {
            this.deserializer = deserializer;
            return this;
        }

        public Builder<REQ, RES>  deserializer(Deserializer deserializer) {
            this.deserializer = toFunction(deserializer, responseBodyType);
            return this;
        }

        public Builder<REQ, RES> group(String group) {
            this.group = group;
            return this;
        }

        public Builder<REQ, RES> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<REQ, RES> method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder<REQ, RES> urlTemplate(ParametrizedString urlTemplate) {
            this.urlTemplate = urlTemplate;
            return this;
        }

        public Builder<REQ, RES> urlTemplate(String urlTemplate) {
            return urlTemplate(new ParametrizedString(urlTemplate, "{{", "}}"));
        }

        public Builder<REQ, RES> requestHeader(Map<String, String> requestHeaders) {
            this.requestHeaders = requestHeaders;
            return this;
        }
        public Builder<REQ, RES> requestHeader(String key, String value) {
            if (this.requestHeaders == null)
                this.requestHeaders = new HashMap<>();
            this.requestHeaders.put(key, value);
            return this;
        }

        public HttpEndpoint<REQ, RES> build() {
            return new HttpEndpoint<REQ, RES>(
                    requestBodyType,
                    responseBodyType,
                    serializer,
                    deserializer,
                    group,
                    name,
                    method,
                    urlTemplate,
                    requestHeaders == null ? Map.of() : requestHeaders
            );
        }
    }


    private static final BiConsumer<OutputStream, ?> DO_NOTHING_SERIALIZER =
            (OutputStream out, Object req) -> {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new HttpClientException(0, e);
                }
            };
    @SuppressWarnings("unchecked")
    static <REQ> BiConsumer<OutputStream, REQ> getDoNothingSerializer() {
        return (BiConsumer<OutputStream, REQ>) DO_NOTHING_SERIALIZER;
    }

    private static final Function<InputStream, ?> DO_NOTHING_DESERIALIZER =
            (InputStream in) -> {
                try {
                    in.close();
                    return null;
                } catch (IOException e) {
                    throw new HttpClientException(0, e);
                }
            };
    @SuppressWarnings("unchecked")
    static <RES> Function<InputStream, RES> getDoNothingDeserializer() {
        return (Function<InputStream, RES>) DO_NOTHING_DESERIALIZER;
    }

    static <REQ> BiConsumer<OutputStream, REQ> toConsumer(Serializer serializer) {
        return (out, req) -> {
            try {
                serializer.serialize(out, req);
            } catch (IOException e) {
                throw new HttpClientException(0, e);
            }
        };
    }

    static <RES> Function<InputStream, RES> toFunction(Deserializer deserializer, Class<RES> responseBodyType) {
        return in -> {
            try {
                return deserializer.deserialize(in, responseBodyType);
            } catch (IOException e) {
                throw new HttpClientException(0, e);
            }
        };
    }
}
