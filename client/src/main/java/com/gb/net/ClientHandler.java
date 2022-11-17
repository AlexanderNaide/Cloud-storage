package com.gb.net;

import com.gb.classes.Command;
import com.gb.classes.command.UpdateCatalog;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Command> {

    private final MessageReceived messageReceived;

    public ClientHandler(MessageReceived messageReceived) {
        this.messageReceived = messageReceived;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command) throws Exception {
        messageReceived.onReceived(command);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush(new UpdateCatalog());
    }
}
