package dev.zykov.resolv.account.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zykov.resolv.account.dto.Account;
import dev.zykov.resolv.datasource.IReaderWriter;
import dev.zykov.resolv.exception.ResolvHttpStatusException;
import dev.zykov.resolv.util.JacksonMapper;

public class AccountDataReaderWriter implements IReaderWriter<Account> {

    private final ObjectMapper objectMapper;

    public AccountDataReaderWriter() {
        this.objectMapper = JacksonMapper.getInstance();
    }
    @Override
    public Account readData(String rawData) {
        try {
            return objectMapper.readValue(rawData, Account.class);
        } catch (JsonProcessingException exception) {
            throw new ResolvHttpStatusException();
        }
    }

    @Override
    public String writeData(Account data){
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException exception) {
            throw new ResolvHttpStatusException();
        }
    }
}
