package top.clarkrao.nettyserver.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.clarkrao.nettyserver.netty.handler.ChildChannelHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ClarkRao
 * @Date: 2019/11/13 22:23
 * @Description: Netty 服务端
 */
@Component
@Slf4j
public class NettyServer {
    public static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    public void start(InetSocketAddress socketAddress) {
        log.info("NettyServer start...");
        //主线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //工作线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //启动辅助类
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChildChannelHandler());

        try {
            //绑定端口，同步等待成功
            ChannelFuture f = bootstrap.bind(socketAddress).sync();
            log.info("服务器启动成功，监听端口{}", socketAddress.getPort());
            //等待服务器监听端口关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
