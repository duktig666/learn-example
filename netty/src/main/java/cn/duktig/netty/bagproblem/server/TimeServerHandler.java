package cn.duktig.netty.bagproblem.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * description: 用于对网络IO 进行读写处理
 * <p>
 * 模拟 粘包 和 拆包问题
 *
 * @author RenShiWei
 * Date: 2022/2/12 16:14
 **/
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /** 记录 接收的数据条数 */
    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 转换成 ByteBuf 对象
        ByteBuf buf = (ByteBuf) msg;
        // 创建 可读字节数 的数组
        byte[] req = new byte[buf.readableBytes()];
        // 将缓冲区的字节数组，复制到新的数组当中
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8)
                .substring(0, req.length - System.getProperty("line.separator").length());
        System.out.println("The time server receive order : " + body + "; the counter is :" + ++ counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");

        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        // 异步发送应答消息给 客户端（write方法只是把待发送的消息放到发送缓冲数组中，真正发送要使用 flush()）
        ctx.write(resp);
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

    /**
     * 连接异常或者退出时 执行
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}

