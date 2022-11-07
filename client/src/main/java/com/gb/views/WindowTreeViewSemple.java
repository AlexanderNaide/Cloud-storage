package com.gb.views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

import java.io.File;
import java.util.LinkedList;

public class WindowTreeViewSemple {

    public VBox VBoxHomeWindow;
//    private final TreeView <String> treeView;
    private TreeView <String> treeView;
    TreeItem<String> root;

    private LinkedList<File> list;

    private Ico ico;


    public WindowTreeViewSemple(VBox VBoxHomeWindow){
        this.VBoxHomeWindow = VBoxHomeWindow;
        treeView = new TreeView<String>();
        this.VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        ico = new IconVer1();



//        treeView.setCellFactory(param -> new TextFieldTreeCell<>(new DefaultStringConverter()));

        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new TextFieldTreeCell<>(new DefaultStringConverter());
            }
        });

        root = new TreeItem<>();
        root.setValue("Home");
        root.setExpanded(true);
        treeView.setRoot(root);

    }

    public void setEditing(ActionEvent actionEvent){
/*
        // Тут все работает как надо

        TreeItem<String> newItem = new TreeItem<>();
        newItem.setValue("Item " + treeView.getExpandedItemCount());
        treeView.getRoot().getChildren().add(newItem);
        treeView.requestFocus();
        treeView.getSelectionModel().select(newItem);
        treeView.edit(newItem);
*/

        treeView.setEditable(true);
        TreeItem<String> newItem = new TreeItem<>();
        newItem.setValue("Item " + treeView.getExpandedItemCount());
        treeView.getRoot().getChildren().add(newItem);
        treeView.requestFocus();
        treeView.getSelectionModel().select(newItem);
        treeView.layout();
        treeView.edit(newItem);
        treeView.setEditable(false);

    }
}
