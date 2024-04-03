package dev.zykov.resolv.server.dto;

import java.util.Objects;

public class RequestObject {

    private RequestContext requestContext;
    private String content;

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestObject that = (RequestObject) o;

        if (!requestContext.equals(that.requestContext)) return false;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        int result = requestContext.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
