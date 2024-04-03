package dev.zykov.resolv.server.configuration;

import dev.zykov.resolv.server.configuration.handler.ServerHandler;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

public class ServerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ServerHandler());
    }


}
