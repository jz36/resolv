package dev.zykov.resolv.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

public class ResolvNotFoundException extends ResolvHttpStatusException {

    public ResolvNotFoundException() {
        super(HttpResponseStatus.NOT_FOUND);
    }

    public ResolvNotFoundException(String message) {
        super(message, HttpResponseStatus.NOT_FOUND);
    }

}
