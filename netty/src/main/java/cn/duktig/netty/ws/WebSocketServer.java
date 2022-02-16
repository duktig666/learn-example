package cn.duktig.netty.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * description: ws协议的Netty服务器
 *
 * @author RenShiWei
 * Date: 2022/2/16 8:55
 **/
public class WebSocketServer {

    private int port = 8081;

    public void run() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //web基于http协议的解码器，将请求和应答消息编码或解码为http消息
                            ch.pipeline().addLast(new HttpServerCodec());
                            //将http消息的多个部分组合成一条完整的Http消息
                            ch.pipeline().addLast(new HttpObjectAggregator(1024 * 64));
                            //对大数据流的支持，向客户端发送H5文件，主要用于支持浏览器和服务端进行websocket通信
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            //websocket服务器处理对协议，用于指定给客户端连接访问的路径
                            //该handler会帮你处理一些繁重的复杂的事
                            //会帮你处理握手动作：handshaking(close,ping,pong) ping + pong = 心跳
                            //对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
                            //添加我们的自定义channel处理器
                            ch.pipeline().addLast(new WebSocketHandler());
                        }
                    });

            System.out.println("服务器启动中,websocket的端口为：" + port);
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            //关闭主从线程池
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        WebSocketServer ws = new WebSocketServer();
        ws.run();
    }

}

