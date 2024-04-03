package dev.zykov.resolv.account.service;

import dev.zykov.resolv.server.dto.RequestObject;
import io.netty.handler.codec.http.FullHttpResponse;

public interface IAccountService {

    FullHttpResponse getAccount(RequestObject requestObject);
    FullHttpResponse createAccount(RequestObject requestObject);
    FullHttpResponse updateAccount(RequestObject requestObject);
    FullHttpResponse deleteAccount(RequestObject requestObject);
}
