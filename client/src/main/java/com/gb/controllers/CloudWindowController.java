package com.gb.controllers;

import com.gb.classes.command.Catalog;
import com.gb.classes.Command;
import com.gb.classes.command.TestCommand;
import com.gb.classes.command.UpdateCatalog;
import com.gb.net.Net;
import com.gb.views.WindowTreeView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class CloudWindowController implements Initializable {
    public VBox VBoxHomeWindow;
    public AnchorPane HomeWindow;

    public WindowTreeView treeView;

    private Net net;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView = new WindowTreeView(VBoxHomeWindow);

        try {
            Socket socket = new Socket("localhost", 6830);
            net = new Net(this::readCommand, socket);


            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    Command c = new UpdateCatalog();
                    net.sendMessages(c);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            t.start();


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readCommand(Command com) {
        if (com instanceof UpdateCatalog){
            System.out.println(com.getName());
        } else if (com instanceof Catalog){

            System.out.println(com.getClass());
            System.out.println(((Catalog) com).getCatalog().toString());
            treeView.updateView(((Catalog) com).getCatalog());
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
}
