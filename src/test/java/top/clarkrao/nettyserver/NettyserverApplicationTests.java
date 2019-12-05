package top.clarkrao.nettyserver;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.clarkrao.nettyserver.codec.protobuf.IMRequestProto;
import top.clarkrao.nettyserver.codec.util.ProtoBufUtil;

import java.util.Objects;

@SpringBootTest
@Slf4j
class NettyserverApplicationTests {

//    @Autowired
//    ZkClient zkClient;

    @Test
    void testZk() {
//        zkClient.create("/testnode","lalala".getBytes());
//
//        byte[] data = zkClient.getData("/testnode");
//
//        log.info("data id {}",new String(data, UTF_8));
//
//        zkClient.setData("/testnode","192.168.1.111:9000".getBytes());
//
//        data = zkClient.getData("/testnode");
//
//        log.info("data is {}",new String(data, UTF_8));
    }

    @Test
    void testProtoBuf() throws InvalidProtocolBufferException {
        IMRequestProto.IMRequest req = createReq();
        log.info("编码前 {}",req.toString());
        IMRequestProto.IMRequest decodeReq = ProtoBufUtil.decode(ProtoBufUtil.encode(req));
        log.info("编码后 {}",decodeReq.toString());

        log.info("是否相等 -----> {}", Objects.equals(req,decodeReq));
    }

    private IMRequestProto.IMRequest createReq() {
        IMRequestProto.IMRequest.Builder builder = IMRequestProto.IMRequest.newBuilder();

        builder.setRequestId(123456);
        builder.setReqMsg("lalala");
        builder.setType(1);

        return builder.build();
    }
}
