package top.clarkrao.nettyserver.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: ClarkRao
 * @Date: 2019/12/5 21:18
 * @Description:
 */
public class HttpServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
//                    .childOption(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new HttpServerInitializer());

            //绑定端口，同步等待成功
            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
