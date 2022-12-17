package com.gb.net;

import com.gb.classes.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.plaf.basic.BasicMenuUI;

public class DownObserver extends ChannelInboundHandlerAdapter {

    private final Pane animatedProgress;

    public DownObserver(Pane animatedProgress) {
        this.animatedProgress = animatedProgress;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        Command command = (Command) msg;

        System.out.println("Принимаем " + command.getName());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);

//        animatedProgress.getChildren().add(new ImageView(new Image("/desk/download.gif", 36, 36, false, false)));
        System.out.println("Закончили читать");
    }
}
