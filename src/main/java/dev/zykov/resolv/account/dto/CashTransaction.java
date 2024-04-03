package dev.zykov.resolv.account.dto;

import dev.zykov.resolv.exception.ResolvBadRequestException;

import java.math.BigDecimal;

public class CashTransaction {

    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashTransaction that = (CashTransaction) o;

        if (!fromAccountId.equals(that.fromAccountId)) return false;
        if (!toAccountId.equals(that.toAccountId)) return false;
        return amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        int result = fromAccountId.hashCode();
        result = 31 * result + toAccountId.hashCode();
        result = 31 * result + amount.hashCode();
        return result;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void validateCashTransaction() {
        validateAccountId(fromAccountId);
        validateAccountId(toAccountId);
        validateAmount(amount);
    }

    private void validateAccountId(String accountId) {
        if (accountId == null || accountId.isBlank()) {
            throw new ResolvBadRequestException("The 'id' field is mandatory and should be presented in the request's params");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResolvBadRequestException("Amount of transaction cannot be 'null' or be less than zero");
        }
    }

}
