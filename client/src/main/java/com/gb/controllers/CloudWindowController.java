package com.gb.controllers;

import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.command.Catalog;
import com.gb.classes.Command;
import com.gb.classes.command.DeleteFile;
import com.gb.classes.command.TestCommand;
import com.gb.classes.command.UpdateCatalog;
import com.gb.net.Net;
import com.gb.views.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CloudWindowController extends WindowTreeView implements Initializable {
    public AnchorPane HomeWindow;

//    public WindowTreeView treeView;
    public TextField interText;

    private Desktop desktop;

    private FileChooser fileChooser;

    private Net net;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(this);
        fileChooser = new FileChooser();
        desktop = Desktop.getDesktop();

//        treeView = new WindowTreeViewOthver(VBoxHomeWindow);
//        treeView = new WindowTreeViewSemple(VBoxHomeWindow);
//        treeView = new WindowTreeViewSempleUser(VBoxHomeWindow);

        try {
            Socket socket = new Socket("localhost", 6830);
            net = new Net(this::readCommand, socket);

            Command c = new UpdateCatalog();
            sendMessages(c);


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessages(Command command) {
        net.sendMessages(command);
    }

    private void readCommand(Command com) {
        if (com instanceof UpdateCatalog){
            System.out.println(com.getName());
        } else if (com instanceof Catalog){
//            treeView.updateView(((Catalog) com).getCatalog());
        } else if (com instanceof MyDirectory){
//            treeView.updateViewNew((MyDirectory) com);
            updateViewNew((MyDirectory) com);
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
        for (File file : files) {
            System.out.println(file);
        }
    }
}
