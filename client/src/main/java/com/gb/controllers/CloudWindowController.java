package com.gb.controllers;

import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.command.Catalog;
import com.gb.classes.Command;
import com.gb.classes.command.TestCommand;
import com.gb.classes.command.UpdateCatalog;
import com.gb.net.Net;
import com.gb.views.WindowTreeView;
import com.gb.views.WindowTreeViewOthver;
import com.gb.views.WindowTreeViewSemple;
import com.gb.views.WindowTreeViewSempleUser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class CloudWindowController implements Initializable {
    public VBox VBoxHomeWindow;
    public AnchorPane HomeWindow;

    public WindowTreeView treeView;
    public TextField interText;

    private Net net;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView = new WindowTreeView(VBoxHomeWindow);
//        treeView = new WindowTreeViewOthver(VBoxHomeWindow);
//        treeView = new WindowTreeViewSemple(VBoxHomeWindow);
//        treeView = new WindowTreeViewSempleUser(VBoxHomeWindow);

        try {
            Socket socket = new Socket("localhost", 6830);
            net = new Net(this::readCommand, socket);

            Command c = new UpdateCatalog();
            net.sendMessages(c);


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readCommand(Command com) {
        if (com instanceof UpdateCatalog){
            System.out.println(com.getName());
        } else if (com instanceof Catalog){
//            treeView.updateView(((Catalog) com).getCatalog());
        } else if (com instanceof MyDirectory){
            treeView.updateViewNew((MyDirectory) com);
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
        net.sendMessages(tc);
    }

    public void UpdateList(ActionEvent actionEvent) {
        UpdateCatalog uc = new UpdateCatalog();
        net.sendMessages(uc);
    }

    public void AddDirectory(ActionEvent actionEvent) {

        treeView.setEditing(actionEvent);
//        interText.setVisible(true);
//        File file = treeView.getParentItem(actionEvent);
//        System.out.println(file.getName());
    }


    public void TextInsered(ActionEvent actionEvent) {
    }
}
