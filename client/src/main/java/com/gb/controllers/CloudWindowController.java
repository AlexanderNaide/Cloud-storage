package com.gb.controllers;

import com.gb.views.WindowTreeView;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CloudWindowController implements Initializable {
    public VBox VBoxHomeWindow;
    public AnchorPane HomeWindow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WindowTreeView treeView = new WindowTreeView(VBoxHomeWindow);
    }
}
