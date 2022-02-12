package cn.duktig.netty.timeserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * description: netty 服务器端 例子
 *
 * @author RenShiWei
 * Date: 2022/2/12 16:00
 **/
public class TimeServer {

    public void bind(int port) throws Exception {

        // 配置 Reactor 线程组
        // 接受 客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 进行SocketChannel的网络读写
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // NIO 服务端启动服务类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            // 绑定端口 同步阻塞，等待绑定操作完成
            ChannelFuture f = b.bind(port).sync();

            // 等待服务端链路关闭后再结束 主函数
            f.channel().closeFuture().sync();
        } finally {
            // 退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }

    }

    public static void main(String[] args) throws Exception {
        int port = 8081;
        new TimeServer().bind(port);
    }

}

