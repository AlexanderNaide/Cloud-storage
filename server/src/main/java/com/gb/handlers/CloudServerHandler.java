package com.gb.handlers;

import com.gb.classes.Command;
import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.MyDir.NotDirectoryException;
import com.gb.classes.command.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.scene.control.TreeItem;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

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
                case "newFile" -> createNewFile(channel, (NewFile) command);
                case "getFile" -> getFile(channel, (GetFile) command);
                case "renameFile" -> renameFile(channel, (RenameFile) command);
            }
        } else {
//            channel.writeAndFlush(command);
        }
    }

    public void renameFile(ChannelHandlerContext channel, RenameFile renameFile) throws NotDirectoryException, IOException {

        Path path = renameFile.getFile().toPath();
        if(Files.exists(path)){
            if (path.getName(0).toString().equals("Root")){
                if (path.getName(1).toString().equals("user1")){

                    renameFile.getFile().renameTo(renameFile.getNewFile());
                }
            }
        }
        updateCatalog(channel);
    }

    public void updateCatalog(ChannelHandlerContext channel) throws NotDirectoryException {
        Command answer = new MyDirectory(Paths.get("Root/user1").toFile());
        channel.writeAndFlush(answer);
//        System.out.println("Новый каталог отправлен");
    }

    public void createNewCatalog(ChannelHandlerContext channel, NewCatalog newCatalog) throws NotDirectoryException, IOException {

        Path newCat = newCatalog.getFile().toPath();
//        System.out.println(newCat);
        if(!Files.exists(newCat)){
            if (newCat.getName(0).toString().equals("Root")){
                if (newCat.getName(1).toString().equals("user1")){
                    Files.createDirectory(newCat);
                }
            }
        }
        updateCatalog(channel);
    }

    public void createNewFile(ChannelHandlerContext channel, NewFile newFile) throws NotDirectoryException, IOException {

        Path createFile = newFile.getFile().toPath();
//        System.out.println(newCat);
        if(!Files.exists(createFile)){
            if (createFile.getName(0).toString().equals("Root")){
                if (createFile.getName(1).toString().equals("user1")){
                    Files.write(createFile, newFile.getDataByte(), StandardOpenOption.CREATE);
                }
            }
        }
        updateCatalog(channel);
    }


    public void getFile(ChannelHandlerContext channel, GetFile getFile) throws NotDirectoryException, IOException {

        File file = getFile.getFile();
        if(!file.isDirectory()){
            File answerFile = new File(getFile.getTargetDir() + "\\" + file.getName());
            byte[] dataByte = Files.readAllBytes(file.toPath());
            Command answer = new NewFile(answerFile, dataByte);
            channel.writeAndFlush(answer);
        }
        updateCatalog(channel);
    }

    public void deleteFile(ChannelHandlerContext channel, DeleteFile deleteFile) throws NotDirectoryException, IOException {

        Path delF = deleteFile.getFile().toPath();
//        System.out.println(delF);
        if (delF.getName(0).toString().equals("Root")){
            if (delF.getName(1).toString().equals("user1")){
                Files.walkFileTree(deleteFile.getFile().toPath(), new SimpleFileVisitor<>(){
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
//                Files.deleteIfExists(delF);
            }
        }
        updateCatalog(channel);
    }

    private Catalog createCatalog(ChannelHandlerContext channel) throws IOException {
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
//        System.out.println("Каталог создан.");
        return catalog;
    }
}
