package com.pb.server.sdk;

import com.pb.server.sdk.filter.MessageDecoder;
import com.pb.server.sdk.filter.MessageEncoder;
import com.pb.server.sdk.util.ContexHolder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class nettyMain {

    public static final int PORT = 8000;
    public static Logger logger = LoggerFactory.getLogger(nettyMain.class);


    private int maxFrameLength = 1024;
    private int lengthFieldOffset = 0;
    private int lengthFieldLength = 4;
    private int lengthAdjustment = 11;//5
    private int initialBytesToStrip = 0;

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
                    //channel.pipeline().addLast(new ObjectEncoder());
                    channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip));
                    channel.pipeline().addLast(new MessageDecoder());
                    //channel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
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
