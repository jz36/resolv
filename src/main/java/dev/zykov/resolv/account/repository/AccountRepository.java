package dev.zykov.resolv.account.repository;

import dev.zykov.resolv.account.dao.AccountDao;
import dev.zykov.resolv.account.dto.Account;
import dev.zykov.resolv.datasource.Datasource;

import java.util.List;

public class AccountRepository {

    private final Datasource datasource;

    public AccountRepository() {
        this.datasource = Datasource.getDatasource();
    }

    public List<AccountDao> findAll(){
        return datasource.getAccountStorage().values().stream().toList();
    }

    public AccountDao findById(String id) {
        return datasource.getAccountStorage().get(id);
    }

    public void modify(AccountDao account) {
        datasource.modifyData(account);
    }

    public void delete(String id) {
        datasource.deleteData(id);
    }
}
