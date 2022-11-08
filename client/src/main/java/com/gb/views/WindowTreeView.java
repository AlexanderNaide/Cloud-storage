package com.gb.views;

import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.command.NewCatalog;
import com.gb.controllers.CloudWindowController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class WindowTreeView {

    protected CloudWindowController controller;
    public VBox VBoxHomeWindow;
    protected TreeView <UserItem> treeView;
    private Ico ico;
    public void initialize(CloudWindowController controller) {

        this.controller = controller;
        treeView = new TreeView<UserItem>();
//        treeView.setShowRoot(false);  // скрывает корневой каталог
        this.VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        ico = new IconVer1();
        treeView.setRoot(new TreeItem<>(new UserItem("", true, "Home"), new ImageView(ico.getIco("home"))));

        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            updateImageForExpanded(treeView.getRoot().getChildren());
        });
        treeView.setCellFactory(param -> new TextFieldTreeCell<UserItem>(new StringConverter<UserItem>() {

            @Override
            public String toString(UserItem object) {
                return object.toString();
            }
            @Override
            public UserItem fromString(String string) {
                updateImageForExpanded(treeView.getRoot().getChildren());
                treeView.requestFocus();
                UserItem userItem = param.getEditingItem().getValue();
                userItem.generateFile(string);
                controller.sendMessages(new NewCatalog(userItem.getFile()));
                return userItem;
            }
        }));
        treeView.getRoot().setExpanded(true);
    }
        private String readTemporaryName (TreeItem<UserItem> item){
            String name = "Новая папка";
            String newName = name;
            boolean x = false;
            int n = 2;
            while (!x){
                for (TreeItem<UserItem> child : item.getChildren()) {
                    if (child.getValue().toString().equals(newName)){
                        newName = name + " " + n;
                        n++;
                        x = true;
                        break;
                    }
                }
                x = !x;
            }
        return newName;
    }


    public void setEditing(ActionEvent actionEvent){

        treeView.setEditable(true);
        TreeItem<UserItem> newItem = new TreeItem<>();
        TreeItem<UserItem> parentItem =  treeView.getSelectionModel().getSelectedItem();
        if (parentItem == null){
            parentItem = treeView.getRoot();
        } else if (!parentItem.getValue().isDir()) {
            parentItem = parentItem.getParent();
        }
        newItem.setValue(new UserItem(parentItem.getValue().getFile() + "\\",true, readTemporaryName(parentItem)));
        parentItem.getChildren().add(0, newItem);
        treeView.requestFocus();
        parentItem.setExpanded(true);
        treeView.getFocusModel().focus(0);
        treeView.layout();
        treeView.edit(newItem);
        treeView.setEditable(false);
    }

    public void updateViewNew(MyDirectory myDirectory) {
        treeView.getRoot().getValue().setFile(myDirectory.getCatalog());
        TreeItem<UserItem> newUserCatalog = new TreeItem<>(new UserItem(myDirectory.getCatalog(), true));
        newUserCatalog.getChildren().addAll(updateViewCat(myDirectory).getChildren());
        updateExpanded(newUserCatalog.getChildren(), treeView.getRoot().getChildren());
        treeView.getRoot().getChildren().clear();
        treeView.getRoot().getChildren().addAll(newUserCatalog.getChildren());

        updateImageForExpanded(treeView.getRoot().getChildren());
    }

    public TreeItem<UserItem> updateViewCat(MyDirectory md) {
        TreeItem<UserItem> item = new TreeItem<>(new UserItem(md.getCatalog(), true), new ImageView(ico.getIco("cat")));
        for (File file : md.getFiles()) {
            item.getChildren().add(new TreeItem<>(new UserItem(file, false), new ImageView(ico.getIco("file"))));
        }
        for (MyDirectory myDirectory : md.getDirectories()) {
            item.getChildren().add(updateViewCat(myDirectory));
        }
        return item;
    }

    public void updateExpanded(ObservableList<TreeItem<UserItem>> newCatalog, ObservableList<TreeItem<UserItem>> oldCatalog) {
        for (TreeItem<UserItem> oldItem : oldCatalog) {
            for (TreeItem<UserItem> item : newCatalog) {
                if (item.getValue().toString().equals(oldItem.getValue().toString())){
                    item.setExpanded(oldItem.isExpanded());
                    if (oldItem.getChildren() != null && item.getChildren() != null){
                        updateExpanded(item.getChildren(), oldItem.getChildren());
                    }
                }
            }
        }
    }

    public void updateImageForExpanded(ObservableList<TreeItem<UserItem>> Catalog) {
        for (TreeItem<UserItem> item : Catalog) {
            if (item.getValue().isDir()){
                if (item.getChildren().size() > 0) {
                    item.setGraphic(item.isExpanded() ? new ImageView(ico.getIco("openCat")) : new ImageView(ico.getIco("cat")));
                    updateImageForExpanded(item.getChildren());
                } else {
                    item.setGraphic(new ImageView(ico.getIco("cat")));
                }
            }
        }
    }
}
