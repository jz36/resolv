package dev.zykov.resolv.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JacksonMapper {

    private static ObjectMapper INSTANCE;

    private JacksonMapper() {}

    public static ObjectMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObjectMapper();
        }
        return INSTANCE;
    }
}
