package dev.zykov.resolv.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

public class ResolvBadRequestException extends ResolvHttpStatusException{

    public ResolvBadRequestException(String message) {
        super(message, HttpResponseStatus.BAD_REQUEST);
    }
}
