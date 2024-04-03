package dev.zykov.resolv.server.configuration.handler;

import dev.zykov.resolv.server.dto.RequestContext;
import dev.zykov.resolv.server.dto.RequestObject;
import dev.zykov.resolv.server.configuration.HttpOrchestrator;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final static ThreadLocal<RequestContext> localRequestContext = new ThreadLocal<>();
    private final HttpOrchestrator httpOrchestrator;

    public ServerHandler() {
        this.httpOrchestrator = new HttpOrchestrator();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof LastHttpContent) {
            final ByteBuf jsonBuf = ((LastHttpContent) msg).content();
            final RequestObject requestObject = new RequestObject();
            requestObject.setRequestContext(localRequestContext.get());
            requestObject.setContent(jsonBuf.toString(CharsetUtil.UTF_8));

            localRequestContext.remove();


            final FullHttpResponse response = httpOrchestrator.processRequestObject(requestObject);
            ctx.write(response);

        } else if (msg instanceof HttpRequest httpRequest) {
            final RequestContext requestContext = new RequestContext();
            requestContext.setHttpMethod(httpRequest.method());
            requestContext.setUri(URLDecoder.decode(httpRequest.uri(), StandardCharsets.UTF_8));
            requestContext.setHeaders(httpRequest.headers().entries().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            localRequestContext.set(requestContext);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }



}
