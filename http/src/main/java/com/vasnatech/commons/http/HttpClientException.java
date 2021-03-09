package com.vasnatech.commons.http;

public class HttpClientException extends RuntimeException {

    int httpStatus;

    public HttpClientException(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpClientException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpClientException(int httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpClientException(int httpStatus, Throwable cause) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public HttpClientException(int httpStatus, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpStatus = httpStatus;
    }
}