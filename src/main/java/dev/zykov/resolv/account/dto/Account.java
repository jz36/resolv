package dev.zykov.resolv.account.dto;

import dev.zykov.resolv.account.dao.AccountDao;

import java.math.BigDecimal;

public final class Account {


    private String accountId;

    private BigDecimal accountCash;

    public Account() {}
    public Account(String id, BigDecimal cash) {
        this.accountCash = cash;
        this.accountId = id;
    }
    public Account(AccountDao account) {
        this.accountId = account.getAccountId();
        this.accountCash = account.getAccountCash();
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountCash(BigDecimal accountCash) {
        this.accountCash = accountCash;
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

        Account account = (Account) o;

        return accountId.equals(account.accountId);
    }

    @Override
    public int hashCode() {
        return accountId.hashCode();
    }
}
