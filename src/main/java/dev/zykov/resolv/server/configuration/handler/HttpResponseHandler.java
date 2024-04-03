package dev.zykov.resolv.server.configuration.handler;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpResponseHandler {

    public static FullHttpResponse http200(final String result) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(result.getBytes()));
        HttpHeaders httpHeaders = response.headers();
        httpHeaders.set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        httpHeaders.set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    public static FullHttpResponse httpError(final Exception exception, HttpResponseStatus errorStatus) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                errorStatus,
                Unpooled.wrappedBuffer(exception.getMessage() == null ? "".getBytes() : exception.getMessage().getBytes())
        );
        HttpHeaders httpHeaders = response.headers();
        httpHeaders.set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        httpHeaders.set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }
}