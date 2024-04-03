package dev.zykov.resolv.account.controller;

import dev.zykov.resolv.account.service.AccountService;
import dev.zykov.resolv.account.service.IAccountService;
import dev.zykov.resolv.rest.CrudController;
import dev.zykov.resolv.server.dto.RequestObject;
import io.netty.handler.codec.http.FullHttpResponse;

public class AccountController implements CrudController {

    private final IAccountService accountService;

    public AccountController() {
        this.accountService = AccountService.getInstance();
    }
    @Override
    public FullHttpResponse get(RequestObject requestObject) {
        return accountService.getAccount(requestObject);
    }

    @Override
    public FullHttpResponse put(RequestObject requestObject) {
        return accountService.updateAccount(requestObject);
    }

    @Override
    public FullHttpResponse post(RequestObject requestObject) {
        return accountService.createAccount(requestObject);
    }

    @Override
    public FullHttpResponse delete(RequestObject requestObject) {
        return accountService.deleteAccount(requestObject);
    }
}
