package dev.zykov.resolv.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

public class ResolvNotImplementedException extends ResolvHttpStatusException{

    public ResolvNotImplementedException() {
        super(HttpResponseStatus.NOT_IMPLEMENTED);
    }

    public ResolvNotImplementedException(String message) {
        super(message, HttpResponseStatus.NOT_IMPLEMENTED);
    }
}
