package dev.zykov.resolv.account.service;

import dev.zykov.resolv.account.dao.AccountDao;
import dev.zykov.resolv.account.data.AccountDataReaderWriter;
import dev.zykov.resolv.account.data.CashTransactionReaderWriter;
import dev.zykov.resolv.account.dto.Account;
import dev.zykov.resolv.account.dto.CashTransaction;
import dev.zykov.resolv.account.repository.AccountRepository;
import dev.zykov.resolv.datasource.IReaderWriter;
import dev.zykov.resolv.exception.ResolvBadRequestException;
import dev.zykov.resolv.exception.ResolvException;
import dev.zykov.resolv.exception.ResolvHttpStatusException;
import dev.zykov.resolv.exception.ResolvNotFoundException;
import dev.zykov.resolv.server.configuration.handler.HttpResponseHandler;
import dev.zykov.resolv.server.dto.RequestObject;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public final class AccountService implements IAccountService {

    private static AccountService INSTANCE;

    private final Object creatingLock;
    private final Object modifyingLock;

    private final AccountRepository accountRepository;
    private final IReaderWriter<Account> accountReaderWriter;

    private final IReaderWriter<CashTransaction> cashTransactionReaderWriter;

    public static AccountService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AccountService();
        }

        return INSTANCE;
    }

    private AccountService() {
        this.accountRepository = new AccountRepository();
        this.accountReaderWriter = new AccountDataReaderWriter();
        this.cashTransactionReaderWriter = new CashTransactionReaderWriter();
        this.creatingLock = new Object();
        this.modifyingLock = new Object();
    }

    @Override
    public FullHttpResponse getAccount(RequestObject requestObject) {
        var uriParams = requestObject.getRequestContext().getUriParams();
        if (uriParams.isEmpty()) {
            var sb = new StringBuilder("[");
            var accounts = accountRepository.findAll();

            sb.append(
                    accounts.stream()
                            .map(Account::new)
                            .map(accountReaderWriter::writeData)
                            .collect(Collectors.joining(",")
                            )
            );

            sb.append("]");
            return HttpResponseHandler.http200(sb.toString());
        } else {
            var id = getIdFromUriParams(uriParams);
            var account = getAccount(id);
            return HttpResponseHandler.http200(accountReaderWriter.writeData(new Account(account)));
        }
    }

    @Override
    public FullHttpResponse createAccount(RequestObject requestObject) {

        var account = accountReaderWriter.readData(requestObject.getContent());

        if (account.getAccountId() == null || account.getAccountId().isBlank()) {
            throw new ResolvBadRequestException("Field 'id' is mandatory and cannot be blank");
        }

        if (account.getAccountCash() == null || account.getAccountCash().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResolvBadRequestException("Account's cash cannot be 'null' or be less than zero");
        }

        synchronized (creatingLock) {
            if (accountRepository.findById(account.getAccountId()) != null) {
                throw new ResolvBadRequestException("Account with the same 'id' already existing");
            }
            accountRepository.modify(new AccountDao(account));
        }
        return HttpResponseHandler.http200("");
    }

    @Override
    public FullHttpResponse updateAccount(RequestObject requestObject) {

        var cashTransaction = cashTransactionReaderWriter.readData(requestObject.getContent());
        cashTransaction.validateCashTransaction();

        synchronized (modifyingLock) {
            var accountFrom = this.getAccount(cashTransaction.getFromAccountId());
            var accountTo = this.getAccount(cashTransaction.getToAccountId());
            var newAccountFromCash = accountFrom.getAccountCash().subtract(cashTransaction.getAmount());
            if (newAccountFromCash.compareTo(BigDecimal.ZERO) < 0) {
                throw new ResolvHttpStatusException("new amount couldn't be less than zero", HttpResponseStatus.NOT_ACCEPTABLE);
            }
            var newAccountToCash = accountTo.getAccountCash().add(cashTransaction.getAmount());
            this.accountRepository.modify(new AccountDao(accountFrom.getAccountId(), newAccountFromCash));
            this.accountRepository.modify(new AccountDao(accountTo.getAccountId(), newAccountToCash));
        }
        return HttpResponseHandler.http200("");
    }

    @Override
    public FullHttpResponse deleteAccount(RequestObject requestObject) {
        var id = getIdFromUriParams(requestObject.getRequestContext().getUriParams());
        accountRepository.delete(id);
        return HttpResponseHandler.http200("");
    }


    private String getIdFromUriParams(Map<String, String> uriParams) {
        if (!uriParams.containsKey("id") || uriParams.get("id").isBlank()) {
            throw new ResolvBadRequestException("The 'id' field is mandatory and should be presented in the request's params");
        }
        return uriParams.get("id");

    }

    private AccountDao getAccount(String id) {
        AccountDao account = accountRepository.findById(id);
        if (account == null) {
            throw new ResolvNotFoundException();
        }
        return account;
    }
}
