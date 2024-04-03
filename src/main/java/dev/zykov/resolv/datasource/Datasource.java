package dev.zykov.resolv.datasource;

import dev.zykov.resolv.account.dao.AccountDao;
import dev.zykov.resolv.account.dto.Account;

import java.util.concurrent.ConcurrentHashMap;

public final class Datasource {

    private static Datasource INSTANCE;

    private final ConcurrentHashMap<String, AccountDao> accountStorage;

    private Datasource(){
        this.accountStorage = new ConcurrentHashMap<>();
    }

    public static Datasource getDatasource() {
        if (INSTANCE == null) {
            INSTANCE = new Datasource();
        }
        return INSTANCE;
    }

    public ConcurrentHashMap<String, AccountDao> getAccountStorage() {
        return new ConcurrentHashMap<>(accountStorage);
    }

    public void modifyData(AccountDao account) {
        this.accountStorage.put(account.getAccountId(), new AccountDao(account));
    }

    public void deleteData(String id) {
        this.accountStorage.remove(id);
    }
}
