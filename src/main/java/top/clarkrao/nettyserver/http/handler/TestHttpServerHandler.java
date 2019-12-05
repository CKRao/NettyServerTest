package top.clarkrao.nettyserver.http.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Objects;

/**
 * @Author: ClarkRao
 * @Date: 2019/12/5 21:28
 * @Description:
 */
@Slf4j
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;

            log.info("请求方法名",httpRequest.method().name());

            URI uri = new URI(httpRequest.uri());

            String text = "Hello World";

            if (Objects.equals("/favicon.ico", uri.getPath())) {
                log.info("请求favicon.ico");
                return;
            } else if (Objects.equals("/test", uri.getPath())){
                text = text + " Test";
            }
            ByteBuf content = Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response);
        }
    }
}
