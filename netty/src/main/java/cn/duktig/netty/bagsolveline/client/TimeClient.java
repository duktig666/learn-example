package cn.duktig.netty.bagsolveline.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2022/2/12 16:37
 **/
public class TimeClient {
    public void connect(int port, String host) throws Exception {//配置客户端NIo线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        /** 初始化时，将Handler设置到ChannelPipeline */
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 解决粘包和粘包问题的核心代码
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            //发起异步连接操作，等待连接成功
            ChannelFuture f = b.connect(host, port).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放NIo线程组
            group.shutdownGracefully();
        }
    }

    /**
     * 按照设计初衷，客户端应该收到100条当前系统时间的消息，但实际上只收到了一条。
     * 这不难理解，因为服务端只收到了2条请求消息，所以实际服务端只发送了2条应答，由于请求消息不满足查询条件，所以返回了2条“BAD
     * ORDER”应答消息。但是实际上客户端只收到了一条包含2条“BAD ORDER”指令的消息，说明服务端返回的应答消息也发生了粘包。
     */
    public static void main(String[] args) throws Exception {
        int port = 8081;
        new TimeClient().connect(port, "127.0.0.1");
    }
}
