package top.clarkrao.nettyserver.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import top.clarkrao.nettyserver.codec.protobuf.IMRequestProto;
import top.clarkrao.nettyserver.netty.handler.logic.IMRequestHandler;

/**
 * @Author: ClarkRao
 * @Date: 2019/11/13 22:27
 * @Description:
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //半包处理器
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        //protobuf解码器
        ch.pipeline().addLast(new ProtobufDecoder(IMRequestProto.IMRequest.getDefaultInstance()));
        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        ch.pipeline().addLast(new ProtobufEncoder());
        ch.pipeline().addLast(new IMRequestHandler());
    }
}
