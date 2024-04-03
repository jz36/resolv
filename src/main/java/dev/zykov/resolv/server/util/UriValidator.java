package dev.zykov.resolv.server.util;

import dev.zykov.resolv.exception.ResolvHttpStatusException;
import io.netty.handler.codec.http.HttpResponseStatus;

public class UriValidator {

    public static final String URI_REGEXP = "^/[a-z]+(($)|(/[0-9]+$)|([?]([a-zA-Z0-9]+=+[a-zA-Z0-9 @.-]+&?)+$))";

    public static void validateURI(final String uri) {
        if (!uri.matches(URI_REGEXP)) {
            throw new ResolvHttpStatusException(
                    "The given path is not supported!",
                    HttpResponseStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
