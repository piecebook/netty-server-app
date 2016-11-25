package com.pb.server.sdk;

import com.pb.server.sdk.filter.MessageDecoder;
import com.pb.server.sdk.filter.MessageEncoder;
import com.pb.server.sdk.filter.MessageProtos;
import com.pb.server.sdk.util.ContexHolder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class nettyMain {

    public static final int PORT = 8000;
    public static Logger logger = LoggerFactory.getLogger(nettyMain.class);


    private int READ_IDLE_TIME_OUT = 5;
    private int WRITE_IDLE_TIME_OUT = 5;
    private int READ_WRITE_IDLE_TIME_OUT = 4;

    public static void main(String[] args) {
        ContexHolder.init();
        nettyMain server = new nettyMain();
        server.start();
    }

    public void start() {
        EventLoopGroup bossgroup = new NioEventLoopGroup();
        EventLoopGroup workergroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossgroup, workergroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel channel)
                        throws Exception {
                    channel.pipeline().addLast(new MessageEncoder());
                    channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                    //channel.pipeline().addLast(new ObjectEncoder());
                    channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                    channel.pipeline().addLast(new MessageDecoder(MessageProtos.MessageProto.getDefaultInstance()));
                    //channel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                    channel.pipeline().addLast("ping", new IdleStateHandler(READ_IDLE_TIME_OUT, WRITE_IDLE_TIME_OUT, READ_WRITE_IDLE_TIME_OUT, TimeUnit.MINUTES));
                    channel.pipeline().addLast((ChannelHandler) ContexHolder.getBean("pbIOHandler"));
                }

            });
            ChannelFuture future = bootstrap.bind(PORT).sync();
            if (future.isSuccess()) {
                System.out.println("Server started!");
                logger.info("Server started!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
