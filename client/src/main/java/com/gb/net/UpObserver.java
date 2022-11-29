package com.gb.net;

import com.gb.classes.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.scene.layout.Pane;

import java.net.SocketAddress;

public class UpObserver extends ChannelOutboundHandlerAdapter {

    private final Pane animatedProgress;

    public UpObserver(Pane animatedProgress) {
        this.animatedProgress = animatedProgress;
    }


    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);

        System.out.println("Закончили отправлять");
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);

        Command command = (Command) msg;

        System.out.println("Отправляем " + command.getName());
    }
}
