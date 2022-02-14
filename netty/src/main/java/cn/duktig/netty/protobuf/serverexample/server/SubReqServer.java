package cn.duktig.netty.protobuf.serverexample.server;

import cn.duktig.netty.bagsolveline.server.TimeServer;
import cn.duktig.netty.protobuf.serverexample.protobuf.SubscribeReqProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * description: PhotoBuf 编解码 Netty服务器
 *
 * @author RenShiWei
 * Date: 2022/2/14 11:18
 **/
public class SubReqServer {

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
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(
                            // 5. channel 代表和客户端进行数据读写的通道 Initializer 初始化，负责添加别的 handler
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    // 6. 添加具体 handler
                                    // 用于半包处理
                                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                                    // Protobuf 解码器
                                    ch.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance())); // 将 ByteBuf 转换为字符串
                                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                                    // Protobuf 编码器，不需要手动编码
                                    ch.pipeline().addLast(new ProtobufEncoder());
                                    ch.pipeline().addLast(new SubReqServerHandler());
                                }
                            })
            ;

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

    public static void main(String[] args) throws Exception {
        int port = 8081;
        new TimeServer().bind(port);
    }
}

