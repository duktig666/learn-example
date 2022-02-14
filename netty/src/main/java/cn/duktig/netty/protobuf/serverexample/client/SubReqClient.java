package cn.duktig.netty.protobuf.serverexample.client;

import cn.duktig.netty.bagsolveline.client.TimeClient;
import cn.duktig.netty.protobuf.serverexample.protobuf.SubscribeRespProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2022/2/14 11:37
 **/
public class SubReqClient {

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
                            // 用于半包处理
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            // Protobuf 解码器
                            ch.pipeline().addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance())); // 将 ByteBuf 转换为字符串
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            // Protobuf 编码器，不需要手动编码
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new SubReqClientHandler());
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

    public static void main(String[] args) throws Exception {
        int port = 8081;
        new TimeClient().connect(port, "127.0.0.1");
    }

}

