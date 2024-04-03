package dev.zykov.resolv.server.configuration;

import dev.zykov.resolv.account.controller.AccountController;
import dev.zykov.resolv.exception.ResolvHttpStatusException;
import dev.zykov.resolv.exception.ResolvNotFoundException;
import dev.zykov.resolv.exception.ResolvNotImplementedException;
import dev.zykov.resolv.rest.CrudController;
import dev.zykov.resolv.server.dto.RequestContext;
import dev.zykov.resolv.server.dto.RequestObject;
import dev.zykov.resolv.server.configuration.handler.HttpResponseHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static dev.zykov.resolv.server.util.UriValidator.validateURI;

public class HttpOrchestrator {

    private final Map<String, CrudController> controllersMap = new HashMap<>();

    {
        controllersMap.put("/accounts", new AccountController());
    }

    public FullHttpResponse processRequestObject(final RequestObject requestObject) {
        try {
            final RequestContext requestContext = requestObject.getRequestContext();
            final CrudController crudController = resolveControllerByPath(requestContext.getUri());
            requestContext.setUriParams(getUriParams(requestContext.getUri()));

            return switch (requestContext.getHttpMethod().name()) {
                case "GET" -> crudController.get(requestObject);
                case "PUT" -> crudController.put(requestObject);
                case "POST" -> crudController.post(requestObject);
                case "DELETE" -> crudController.delete(requestObject);
                default -> throw new ResolvNotImplementedException("The given http method is not supported!");
            };
        } catch (ResolvHttpStatusException ex) {
            return HttpResponseHandler.httpError(ex, ex.getExceptionStatus());
        } catch (RuntimeException ex) {
            return HttpResponseHandler.httpError(ex, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, String> getUriParams(final String uri) {
        if (uri.contains("?")) {
            final String paramsString = uri.substring(uri.indexOf('?') + 1);

            // in case of duplicated params, will be used la last value
            return Arrays.stream(paramsString.contains("&") ? paramsString.split("&") : new String[]{paramsString})
                    .map(value -> value.split("="))
                    .collect(Collectors.toMap(paramValue -> paramValue[0], paramValue -> paramValue[1], (v1, v2) -> v2));
        }
        return new HashMap<>();
    }

    private CrudController resolveControllerByPath(final String currentPath) {
        validateURI(currentPath);

        String rawPath = currentPath.split("\\?")[0];

        final Optional<String> matchingKey = controllersMap.keySet().stream()
                .filter(rawPath::equals)
                .findFirst();

        return matchingKey.map(controllersMap::get).orElseThrow(() -> new ResolvNotFoundException("Not found!"));
    }
}
