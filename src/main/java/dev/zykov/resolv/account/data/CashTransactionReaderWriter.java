package dev.zykov.resolv.account.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zykov.resolv.account.dto.CashTransaction;
import dev.zykov.resolv.datasource.IReaderWriter;
import dev.zykov.resolv.exception.ResolvHttpStatusException;
import dev.zykov.resolv.util.JacksonMapper;

public class CashTransactionReaderWriter implements IReaderWriter<CashTransaction> {

    private final ObjectMapper mapper;

    public CashTransactionReaderWriter() {
        this.mapper = JacksonMapper.getInstance();
    }

    @Override
    public CashTransaction readData(String rawData) {
        try {
            return this.mapper.readValue(rawData, CashTransaction.class);
        } catch (JsonProcessingException exception) {
            throw new ResolvHttpStatusException(exception.getMessage());
        }
    }

    @Override
    public String writeData(CashTransaction data) {
        try {
            return this.mapper.writeValueAsString(data);
        } catch (JsonProcessingException exception) {
            throw new ResolvHttpStatusException(exception.getMessage());
        }
    }
}
