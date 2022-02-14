package cn.duktig.netty.protobuf.serverexample.client;

import cn.duktig.netty.protobuf.serverexample.protobuf.SubscribeReqProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2022/2/14 11:39
 **/
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 10; i++) {
            ctx.write(this.subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("Lilinfeng");
        builder.setProductName("Netty Book ");
        builder.setAddress("NanJing YuHuaTai");
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /*
        发送队列中的消息写入到SocketChannel 中发送给对方。

        从性能角度考虑，为了防止频繁地唤醒Selector进行消息发送，Netty的 write方法并不直接将消息写入SocketChannel 中，
        调用write方法只是把待发送的消息放到发送缓冲数组中，再通过调用flush方法，
        将发送缓冲区中的消息全部写到SocketChannel 中。
         */
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //释放资源
        System.out.println("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }

}
