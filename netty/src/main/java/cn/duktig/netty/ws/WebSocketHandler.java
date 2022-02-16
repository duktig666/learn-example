package cn.duktig.netty.ws;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2022/2/16 9:13
 **/
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    /** 用于记录和管理所有客户端的channel，客户端组 */
    public static ChannelGroup channelGroup;

    static {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //文本消息
        if (msg instanceof TextWebSocketFrame) {
            //第一次连接成功后，给客户端发送消息
            Channel channel = ctx.channel();
            String message = ((TextWebSocketFrame) msg).text();
            channel.writeAndFlush(new TextWebSocketFrame("收到消息：" + message));

            //获取当前channel绑定的IP地址
//            InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
//            String address = ipSocket.getAddress().getHostAddress();
//            System.out.println("address为:" + address);

            //将IP和channel的关系保存
//            if (! channelMap.containsKey(address)) {
//                channelMap.put(address, ctx.channel());
//            }
        }
        //二进制消息
        if (msg instanceof BinaryWebSocketFrame) {
            System.out.println("收到二进制消息：" + ((BinaryWebSocketFrame) msg).content().readableBytes());
            BinaryWebSocketFrame binaryWebSocketFrame =
                    new BinaryWebSocketFrame(Unpooled.buffer().writeBytes("hello".getBytes()));
            //给客户端发送的消息
            ctx.channel().writeAndFlush(binaryWebSocketFrame);
        }
        //ping消息
        if (msg instanceof PongWebSocketFrame) {
            System.out.println("客户端ping成功");
        }
        //关闭消息
        if (msg instanceof CloseWebSocketFrame) {
            System.out.println("客户端关闭，通道关闭");
            Channel channel = ctx.channel();
            channel.close();
        }
    }

    /**
     * 当客户端连接服务端之后(打开连接)
     * 获取客户端的channel,并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved,ChannelGroup会自动移除对应的客户端的channel
        //所以下面这条语句可不写
        //  channelGroup.remove(ctx.channel());
        System.out.println("客户端断开，channel对应的长id为:" + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应的短id为:" + ctx.channel().id().asShortText());
    }


    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("连接异常：" + cause.getMessage());
        cause.printStackTrace();
        ctx.channel().close();
        channelGroup.remove(ctx.channel());
    }


}

