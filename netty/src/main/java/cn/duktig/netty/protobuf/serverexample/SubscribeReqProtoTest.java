package cn.duktig.netty.protobuf.serverexample;

import cn.duktig.netty.protobuf.serverexample.protobuf.SubscribeReqProto;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2022/2/14 11:07
 **/
public class SubscribeReqProtoTest {

    /**
     * photobuf 编码
     */
    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    /**
     * photobuf 解码
     */
    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("Lilinfeng");
        builder.setProductName("Netty Book ");
        builder.setAddress("NanJing YuHuaTai");
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("Before encode : " + req.toString());

        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("After decode : " + req.toString());

        System.out.println("Assert equal : --> " + req2.equals(req));
    }

}
