package top.clarkrao.nettyserver.netty.handler.logic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import top.clarkrao.nettyserver.codec.protobuf.IMRequestProto;
import top.clarkrao.nettyserver.codec.protobuf.IMResponseProto;
import top.clarkrao.nettyserver.netty.NettyServer;

/**
 * @Author: ClarkRao
 * @Date: 2019/11/17 15:57
 * @Description:
 */
@Slf4j
public class IMRequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive...");
        //保存到channelMap
        NettyServer.channelMap.put(ctx.name(), ctx.channel());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        IMRequestProto.IMRequest imRequest = (IMRequestProto.IMRequest) msg;
        log.info("msg {} , {} ,{}",imRequest.getReqMsg(),imRequest.getRequestId(),imRequest.getType());
        ctx.writeAndFlush(resp(imRequest));
    }

    private IMResponseProto.IMResponse resp(IMRequestProto.IMRequest imRequest) {
        IMResponseProto.IMResponse.Builder builder = IMResponseProto.IMResponse.newBuilder();

        builder.setType(imRequest.getType() + 1);
        builder.setResponseId(imRequest.getRequestId() + 1);
        builder.setResMsg("服务端已收到你的消息 ： " + imRequest.getReqMsg());

        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught...");
        ctx.close();
    }
}


