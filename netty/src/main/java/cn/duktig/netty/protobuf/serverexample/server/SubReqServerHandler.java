package cn.duktig.netty.protobuf.serverexample.server;

import cn.duktig.netty.protobuf.serverexample.protobuf.SubscribeReqProto;
import cn.duktig.netty.protobuf.serverexample.protobuf.SubscribeRespProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2022/2/14 11:24
 **/
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if ("Lilinfeng".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Service accept client subscribe req : [ " + req.toString() + "]");
            ctx.writeAndFlush(this.resp(req.getSubReqID()));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqId) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqId(subReqId);
        builder.setRespCode(0);
        builder.setDesc("handle success!");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //释放资源
        System.out.println("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }

}
