package com.gb.handlers;

import com.gb.classes.Command;
import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.MyDir.NotDirectoryException;
import com.gb.classes.command.Catalog;
import com.gb.classes.command.DeleteFile;
import com.gb.classes.command.NewCatalog;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Slf4j
public class CloudServerHandler extends SimpleChannelInboundHandler<Command> {

    @Override
    protected void channelRead0(ChannelHandlerContext channel, Command command) throws Exception {
        log.debug("Received: {}", command);
        if (command != null){
            String com = command.getName();
            switch (com) {
                case "UpdateCatalog" -> updateCatalog(channel);
                case "Test" -> System.out.println("Test");
                case "newCatalog" -> createNewCatalog(channel, (NewCatalog) command);
                case "deleteFile" -> deleteFile(channel, (DeleteFile) command);
            }
        } else {
//            channel.writeAndFlush(command);
        }
    }

    public void updateCatalog(ChannelHandlerContext ctx) throws NotDirectoryException {
        Command answer = new MyDirectory(Paths.get("Root/user1").toFile());
        ctx.write(answer);
    }

    public void createNewCatalog(ChannelHandlerContext ctx, NewCatalog newCatalog) throws NotDirectoryException, IOException {

        Path newCat = newCatalog.getFile().toPath();
        System.out.println(newCat);
        if(!Files.exists(newCat)){
            if (newCat.getName(0).toString().equals("Root")){
                if (newCat.getName(1).toString().equals("user1")){
                    Files.createDirectory(newCat);
                }
            }
        }
        updateCatalog(ctx);
    }

    public void deleteFile(ChannelHandlerContext ctx, DeleteFile deleteFile) throws NotDirectoryException, IOException {

        Path delF = deleteFile.getFile().toPath();
        System.out.println(delF);
        if (delF.getName(0).toString().equals("Root")){
            if (delF.getName(1).toString().equals("user1")){
                Files.deleteIfExists(delF);
            }
        }
        updateCatalog(ctx);
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
