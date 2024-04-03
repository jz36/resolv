package dev.zykov.resolv.server.dto;

import io.netty.handler.codec.http.HttpMethod;

import java.util.Map;
import java.util.Objects;

public class RequestContext {

    private HttpMethod httpMethod;
    private String uri;
    private Map<String, String> uriParams;
    private Map<String, String> headers;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getUriParams() {
        return uriParams;
    }

    public void setUriParams(Map<String, String> uriParams) {
        this.uriParams = uriParams;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestContext that = (RequestContext) o;

        if (!httpMethod.equals(that.httpMethod)) return false;
        if (!uri.equals(that.uri)) return false;
        if (!Objects.equals(uriParams, that.uriParams)) return false;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        int result = httpMethod.hashCode();
        result = 31 * result + uri.hashCode();
        result = 31 * result + (uriParams != null ? uriParams.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }
}
