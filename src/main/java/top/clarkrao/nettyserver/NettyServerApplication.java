package top.clarkrao.nettyserver;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import top.clarkrao.nettyserver.netty.NettyServer;

import java.net.InetSocketAddress;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author clarkrao
 */
@SpringBootApplication
public class NettyServerApplication implements CommandLineRunner {

    private final NettyServer nettyServer;

    @Value("${clark.netty.address}")
    private String address;

    @Value("${clark.netty.port}")
    private int port;

    /**
     * 线程池名称
     */
    private ThreadFactory threadFactory = new DefaultThreadFactory("netty-server-thread");

    /**
     * 线程池
     */
    private final ThreadPoolExecutor singleThread = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), threadFactory);

    public NettyServerApplication(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    public static void main(String[] args) {
//        SpringApplication.run(NettyServerApplication.class, args);

        //设置非web方式启动
        new SpringApplicationBuilder(NettyServerApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        //项目启动后启用线程执行netty server
        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
        singleThread.execute(() -> nettyServer.start(socketAddress));
        //todo: 同时向zookeeper注册信息
    }
}
