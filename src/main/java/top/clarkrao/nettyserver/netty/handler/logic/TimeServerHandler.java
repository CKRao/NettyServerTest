package top.clarkrao.nettyserver.netty.handler.logic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import top.clarkrao.nettyserver.netty.NettyServer;

import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.*;

/**
 * @Author: ClarkRao
 * @Date: 2019/11/13 22:28
 * @Description: 时间服务器逻辑处理器
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive...");
        //保存到channelMap
        NettyServer.channelMap.put(ctx.name(), ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelRead...");
        //处理接受消息的具体逻辑
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, UTF_8);
        log.info("Server received {}", body);
        String currentTime = "QUERY TIME ORDER".equals(body) ? new Date().toString() : "BAD ERROR";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete...");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught...");
        ctx.close();
    }
}
