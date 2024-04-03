package dev.zykov.resolv.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

public class ResolvHttpStatusException extends ResolvException{

    private HttpResponseStatus exceptionStatus;

    public ResolvHttpStatusException() {
        super();
        this.exceptionStatus = HttpResponseStatus.INTERNAL_SERVER_ERROR;
    }

    public ResolvHttpStatusException(String message) {
        super(message);
        this.exceptionStatus = HttpResponseStatus.INTERNAL_SERVER_ERROR;
    }

    public ResolvHttpStatusException(HttpResponseStatus exceptionStatus) {
        super();
        this.exceptionStatus = exceptionStatus;
    }

    public ResolvHttpStatusException(String message, HttpResponseStatus exceptionStatus){
        super(message);
        this.exceptionStatus = exceptionStatus;
    }

    public HttpResponseStatus getExceptionStatus() {
        return this.exceptionStatus;
    }
}
