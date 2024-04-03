package dev.zykov.resolv.datasource;

public interface IReaderWriter<T> {

    T readData(String rawData);

    String writeData(T data);
}
