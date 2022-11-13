package com.gb.net;

import com.gb.classes.Command;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyNet {
    private SocketChannel channel;
    private MessageReceived received;

    public NettyNet(MessageReceived received) {
        this.received = received;
        new Thread(() ->{
            EventLoopGroup group = new NioEventLoopGroup();
            try{
                Bootstrap bootstrap = new Bootstrap();
                ChannelFuture future = bootstrap.channel(NioSocketChannel.class)
                        .group(group)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(
                                        new ObjectEncoder(),
                                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                        new ClientHandler(received)
                                );
                            }
                        }).connect("localhost", 6830).sync();
                future.channel().closeFuture().sync();
            } catch (Exception e){
                log.error("e=", e);
            } finally {
                group.shutdownGracefully();
            }
        }).start();
        System.out.println("Клиент запустился?");
    }

    public void sendMessages(Command com){
        channel.writeAndFlush(com);
    }
}
