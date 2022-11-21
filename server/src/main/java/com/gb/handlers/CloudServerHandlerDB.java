package com.gb.handlers;

import com.gb.OperatorBD;
import com.gb.classes.Command;
import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.MyDir.NotDirectoryException;
import com.gb.classes.command.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static com.gb.OperatorBD.*;

@Slf4j
public class CloudServerHandlerDB extends SimpleChannelInboundHandler<Command> {

    @Override
    protected void channelRead0(ChannelHandlerContext channel, Command command) throws Exception {
//        channelX = channel;
        log.debug("Received: {}", command);
        if (command != null){
            String com = command.getName();
            switch (com) {
                case "userConnect" -> userConnect(channel, (UserConnect) command);
                case "userCreate" -> userCreate(channel, (UserCreate) command);
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

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clearConnect(ctx);
        super.channelInactive(ctx);
    }

    public void userConnect(ChannelHandlerContext channel, UserConnect userConnect) throws NotDirectoryException {
        if (userConnections(channel, userConnect)){
            updateCatalog(channel);
        }
    }

    public void userCreate(ChannelHandlerContext channel, UserCreate userCreate) throws NotDirectoryException, IOException {
        if (newUser(userCreate, "Root/")){
            createCatalog("Root/" + userCreate.getLogin());
            userConnect(channel, new UserConnect(userCreate.getLogin(), userCreate.getPassword()));
        } else {
            channel.writeAndFlush(new MyMessage(new String("Пользователь с таким логином уже существует.".getBytes(StandardCharsets.UTF_8))));
        }
    }

    public void renameFile(ChannelHandlerContext channel, RenameFile renameFile) throws NotDirectoryException {
        Path path = Path.of(OperatorBD.userRootCatalog(channel) + renameFile.getFile().toPath());
        if(Files.exists(path)){
            path.toFile().renameTo(Path.of(userRootCatalog(channel) + renameFile.getNewFile().getPath()).toFile());
        }
        updateCatalog(channel);
    }

    public void updateCatalog(ChannelHandlerContext channel) throws NotDirectoryException {
        Command answer = new MyDirectory(userRootCatalog(channel), userCatalog(channel));
        channel.writeAndFlush(answer);
    }

    public void createNewCatalog(ChannelHandlerContext channel, NewCatalog newCatalog) throws NotDirectoryException, IOException {
        Path newCat = Path.of(OperatorBD.userRootCatalog(channel) + newCatalog.getFile().toPath());
        if(!Files.exists(newCat)){
            Files.createDirectory(newCat);
        }
        updateCatalog(channel);
    }

    public void createNewFile(ChannelHandlerContext channel, NewFile newFile) throws NotDirectoryException, IOException {
        Path createFile = Path.of(OperatorBD.userRootCatalog(channel) + newFile.getFile().toPath());
        if(!Files.exists(createFile)){
            Files.write(createFile, newFile.getDataByte(), StandardOpenOption.CREATE);
        }
        updateCatalog(channel);
    }


    public void getFile(ChannelHandlerContext channel, GetFile getFile) throws NotDirectoryException, IOException {
        Path file = (Path.of(OperatorBD.userRootCatalog(channel) + getFile.getFile().toPath()));
        if(!file.toFile().isDirectory()){
            File answerFile = new File(getFile.getTargetDir() + "\\" + file.getFileName());
            byte[] dataByte = Files.readAllBytes(file);
            Command answer = new NewFile(answerFile, dataByte);
            channel.writeAndFlush(answer);
        }
        updateCatalog(channel);
    }

    public void deleteFile(ChannelHandlerContext channel, DeleteFile deleteFile) throws NotDirectoryException, IOException {
        Path delF = Path.of(OperatorBD.userRootCatalog(channel) + deleteFile.getFile().toPath());
        Files.walkFileTree(delF, new SimpleFileVisitor<>(){
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
        updateCatalog(channel);
    }

    private void createCatalog(String dir) throws IOException {
        Path home = Path.of(dir);
        Files.createDirectory(home);
    }
}
