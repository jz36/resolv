package dev.zykov.resolv.rest;

import dev.zykov.resolv.server.dto.RequestObject;
import io.netty.handler.codec.http.FullHttpResponse;

public interface CrudController {

    FullHttpResponse get(RequestObject requestObject);

    FullHttpResponse put(RequestObject requestObject);

    FullHttpResponse post(RequestObject requestObject);

    FullHttpResponse delete(RequestObject requestObject);
}
