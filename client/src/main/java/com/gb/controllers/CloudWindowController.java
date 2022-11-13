package com.gb.controllers;

import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.command.*;
import com.gb.classes.Command;
import com.gb.net.NettyNet;
import com.gb.views.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class CloudWindowController extends WindowTreeView implements Initializable {
    public AnchorPane HomeWindow;

//    public WindowTreeView treeView;
    public TextField interText;

    private Desktop desktop;

    private FileChooser fileChooser;

//    private Net net;
    private NettyNet net;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(this);
        fileChooser = new FileChooser();
        desktop = Desktop.getDesktop();

        net = new NettyNet(this::readCommand);
    }

    public void sendMessages(Command command) {
        net.sendMessages(command);
    }

    private void readCommand(Command command) {

//        System.out.println(" явообще что-то получаю? ");

        log.debug("Received: {}", command);
        if (command != null){
            String com = command.getName();
            switch (com) {
                case "UpdateCatalog" -> System.out.println("Update catalog");
                case "Test" -> System.out.println("Test");
                case "myDirectory" -> updateViewNew((MyDirectory) command);
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

    public void AddFile(ActionEvent actionEvent) {
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
}
