package com.gb.handlers;

import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.MyMessage;
import com.gb.classes.command.Catalog;
import com.gb.classes.Command;
import com.gb.classes.command.TestCommand;
import com.gb.classes.command.UpdateCatalog;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.File;
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
                    System.out.println("Клент " + ctx.channel().id() + " хочет обновить каталог: ");

                    /**    Вот это работает *******
                    Catalog answer = createCatalog(ctx);
                     */

                    Command answer = new MyDirectory(Paths.get("Root/user1").toFile());

//                    Catalog answer = createTestCatalog(ctx);
//                    System.out.println("Новый каталог: " + answer.getClass());
//                    Catalog answer = new Catalog();
//                    createTestCatalog(ctx, answer);
                    ctx.write(answer);
                    System.out.println("Каталог отправлен");
                } else if (msg instanceof TestCommand) {
                    System.out.println("Пришло тест.");
                    TestCommand answer = new TestCommand();
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

/*    private void createTestCatalog(ChannelHandlerContext ctx, Catalog cat) {

        cat.add(new File("server/2.txt"), new File("server"));
        System.out.println("Тест каталог создан");
    }*/

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
//        final File[] path = new File[1];
        Files.walkFileTree(home, new SimpleFileVisitor<Path>() {


            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!dir.getFileName().toString().equals("user1")){
                    catalog.add(dir);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                catalog.add(file);
                return FileVisitResult.CONTINUE;
            }
        });
        System.out.println("Каталог создан.");
        return catalog;
    }
}
