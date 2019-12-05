package top.clarkrao.nettyserver.codec.util;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import top.clarkrao.nettyserver.codec.protobuf.IMRequestProto;

/**
 * @Author: ClarkRao
 * @Date: 2019/11/17 15:33
 * @Description:
 */
@Slf4j
public class ProtoBufUtil {

    /**
     * IM请求编码
     * @param req
     * @return
     */
    public static byte[] encode(IMRequestProto.IMRequest req) {
        return req.toByteArray();
    }

    /**
     * IM请求解码
     * @param body
     * @return
     * @throws InvalidProtocolBufferException
     */
    public static IMRequestProto.IMRequest decode(byte[] body) throws InvalidProtocolBufferException {
        return IMRequestProto.IMRequest.parseFrom(body);
    }
}
