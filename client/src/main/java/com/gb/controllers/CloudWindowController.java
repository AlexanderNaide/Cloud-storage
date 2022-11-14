package com.gb.controllers;

import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.MyDir.NotDirectoryException;
import com.gb.classes.command.*;
import com.gb.classes.Command;
import com.gb.net.NettyNet;
import com.gb.views.*;
import io.netty.channel.ChannelHandlerContext;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class CloudWindowController extends WindowTreeView implements Initializable {
    public AnchorPane HomeWindow;

//    public WindowTreeView treeView;
    public TextField interText;

//    private Desktop desktop;

    private FileChooser fileChooser;

    private DirectoryChooser directoryChooser;

//    private Net net;
    private NettyNet net;


    /**
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */


// TODO: 014 14.11.22 Необходимо добавить всплывающие сообщения: если файл удаляется, если удаляется дирректория, если скачиваемый файл уже есть, если качается дирректория

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(this);
        fileChooser = new FileChooser();
        directoryChooser = new DirectoryChooser();
//        desktop = Desktop.getDesktop();

        net = new NettyNet(this::readCommand);
    }

    public void sendMessages(Command command) {
        net.sendMessages(command);
    }

    private void readCommand(Command command) {

//        System.out.println(" Явообще что-то получаю? ");

        log.debug("Received: {}", command);
        if (command != null){
            String com = command.getName();
            switch (com) {
                case "UpdateCatalog" -> System.out.println("Update catalog");
                case "Test" -> System.out.println("Test");
                case "myDirectory" -> updateViewNew((MyDirectory) command);
                case "newFile" -> createNewFile((NewFile) command);
            }
        }




/*        Platform.runLater(() -> {
            statuses.getItems().add(message);
        });*/


        /*
        Platform.runLater(() -> {
            listView.getItems().add(message);
        });
        */
    }


    public void TestButton (ActionEvent actionEvent) {
        TestCommand tc = new TestCommand();
        sendMessages(tc);
    }

    public void createNewFile(NewFile newFile){
        try {
            Path createFile = Paths.get(newFile.getFile().getPath());
            Files.write(createFile, newFile.getDataByte(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateList(ActionEvent actionEvent) {
        UpdateCatalog uc = new UpdateCatalog();
        sendMessages(uc);
    }

    public void AddDirectory(ActionEvent actionEvent) {
        setEditing(actionEvent);
    }


    public void TextInsered(ActionEvent actionEvent) {
    }

    public void DeleteButton(ActionEvent actionEvent) {
        TreeItem<UserItem> item = treeView.getFocusModel().getFocusedItem();
        if (item != treeView.getRoot()){
            File file = item.getValue().getFile();
            DeleteFile del = new DeleteFile(file);
            sendMessages(del);
        }
    }

    public void AddFile(ActionEvent actionEvent) throws IOException {
/*
        Вот эта тема загружает Эксплорер в указанной папке

        String onlyPath = "D:\\GAME OF Thrones";
        String completeCmd = "explorer.exe /select," + onlyPath;
        new ProcessBuilder(("explorer.exe " + completeCmd).split(" ")).start();
        */
//        new ProcessBuilder("explorer.exe").start(); // а вот конкретно так стартует библиотека пользователя


        List<File> files = fileChooser.showOpenMultipleDialog(HomeWindow.getScene().getWindow());
        TreeItem<UserItem> parentItem = super.getParentItem();
        parentItem.setExpanded(true);
        for (File file : files) {
            try {
//                byte[] dataByte = Files.readAllBytes(Paths.get(file.getPath()));
                byte[] dataByte = Files.readAllBytes(file.toPath());
                String newFileName = parentItem.getValue().getFile().getPath() + "\\" + file.getName();
                NewFile newFie = new NewFile(new File(newFileName), dataByte);
                sendMessages(newFie);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void DownloadButton(ActionEvent actionEvent) {
        File dir = directoryChooser.showDialog(HomeWindow.getScene().getWindow());
        ObservableList<TreeItem<UserItem>> list = treeView.getSelectionModel().getSelectedItems();
        for (TreeItem<UserItem> userItemTreeItem : list) {
            File file = userItemTreeItem.getValue().getFile();
            sendMessages(new GetFile(file, dir.getPath()));
        }
    }
}
