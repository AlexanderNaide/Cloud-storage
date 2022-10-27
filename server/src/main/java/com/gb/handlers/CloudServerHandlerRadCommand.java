package com.gb.handlers;

import com.gb.classes.MyMessage;
import com.gb.classes.command.Catalog;
import com.gb.classes.Command;
import com.gb.classes.command.UpdateCatalog;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CloudServerHandlerRadCommand extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            if (ctx == null){
                return;
            }
//            System.out.println(msg.getClass());
            if (msg instanceof Command){
                if (msg instanceof UpdateCatalog){
                    System.out.println("Клент хочет обновить каталог: ");
                    Catalog answer = createCatalog(ctx);
                    ctx.write(answer);
                }

            } else if (msg instanceof MyMessage) {
                System.out.println("Client text message: " + ((MyMessage) msg).getText());
                MyMessage answer = new MyMessage("Hello Client!");
                ctx.write(answer);
            } else {
                System.out.println("Server received wrong object!");
            }

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private Catalog createCatalog(ChannelHandlerContext ctx) throws IOException {
        Path home = Paths.get( "root/" + "user1");
        Catalog catalog = new Catalog();
        final Path[] path = new Path[1];
        Files.walkFileTree(home, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                catalog.add(dir, path[0]);
                path[0] = dir;
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                catalog.add(file, path[0]);
                return FileVisitResult.CONTINUE;
            }
        });
        System.out.println("Каталог создан.");
        return catalog;
    }
}
