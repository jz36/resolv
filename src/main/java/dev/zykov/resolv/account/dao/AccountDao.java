package dev.zykov.resolv.account.dao;

import dev.zykov.resolv.account.dto.Account;

import java.math.BigDecimal;

public final class AccountDao {


    private final String accountId;

    private final BigDecimal accountCash;

    public AccountDao(String id, BigDecimal cash) {
        this.accountCash = cash;
        this.accountId = id;
    }

    public AccountDao(AccountDao account) {
        this.accountId = account.getAccountId();
        this.accountCash = account.getAccountCash();
    }

    public AccountDao(Account account) {
        this.accountId = account.getAccountId();
        this.accountCash = account.getAccountCash();
    }
    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getAccountCash() {
        return accountCash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountDao account = (AccountDao) o;

        return accountId.equals(account.getAccountId());
    }

    @Override
    public int hashCode() {
        return accountId.hashCode();
    }
}

