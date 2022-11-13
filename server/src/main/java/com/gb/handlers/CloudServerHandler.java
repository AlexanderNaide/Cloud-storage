package com.gb.handlers;

import com.gb.classes.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudServerHandler extends SimpleChannelInboundHandler<Command> {

    @Override
    protected void channelRead0(ChannelHandlerContext channel, Command command) throws Exception {
        log.debug("Received: {}", command);
        System.out.println(command.getName());
        channel.writeAndFlush(command);
    }
}
