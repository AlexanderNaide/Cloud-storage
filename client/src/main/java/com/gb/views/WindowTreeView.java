package com.gb.views;

import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.command.NewCatalog;
import com.gb.classes.command.RenameFile;
import com.gb.controllers.CloudWindowController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.control.skin.TreeCellSkin;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.awt.*;
import java.io.File;

import static javafx.scene.control.TreeView.editAnyEvent;
import static javafx.scene.control.TreeView.editCancelEvent;

public class WindowTreeView {

    protected CloudWindowController controller;
    public VBox VBoxHomeWindow;
    protected TreeView <UserItem> treeView;
    protected Ico ico;
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

        treeView.setOnEditCommit(event -> {
            treeView.requestFocus();
            UserItem userItem = event.getTreeItem().getValue();
            treeView.setEditable(false);
            updateImageForExpanded(treeView.getRoot().getChildren());



            if (userItem.isRename() == null){
                controller.sendMessages(new NewCatalog(userItem.getFile()));
            } else {
                controller.sendMessages(new RenameFile(userItem.isRename(), userItem.getFile()));
                userItem.renameFinished();
            }

        });

        treeView.setOnEditCancel(event -> {

            // https://examples.javacodegeeks.com/core-java/javafx-treeview-example/

            TreeItem<UserItem> item = event.getTreeItem();
            if (item.getValue().isRename() == null){
                TreeItem<UserItem> parentItem = event.getTreeItem().getParent();
                parentItem.getChildren().remove(event.getTreeItem());
            } else {
                item.getValue().renameFinished();
            }
            treeView.setEditable(false);
        });



/*        treeView.setCellFactory(param -> new TextFieldTreeCell<UserItem>(){
            @Override
            public void updateItem(UserItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setStyle("-fx-indent: 30;");
                }
            }
        });*/



        treeView.setCellFactory(param -> new TextFieldTreeCell<UserItem>(new StringConverter<UserItem>(){
            @Override
            public String toString(UserItem object) {
                return object.toString();
            }
            @Override
            public UserItem fromString(String string) {
                UserItem userItem = param.getEditingItem().getValue();
                userItem.renameFile(string);
//                userItem.generateFile(string);

                return userItem;
            }
        }){
            @Override
            public void updateItem(UserItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setStyle("-fx-indent: 30;");
                }
            }
        });


        treeView.getRoot().setExpanded(true);
    }
        protected String readTemporaryName(TreeItem<UserItem> item){
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

    public TreeItem<UserItem> getParentItem(){
        TreeItem<UserItem> parentItem = treeView.getSelectionModel().getSelectedItem();
        if (parentItem == null){
            parentItem = treeView.getRoot();
        } else if (!parentItem.getValue().isDir()) {
            parentItem = parentItem.getParent();
        }
        return parentItem;
    }


    public void setEditing(TreeItem<UserItem> item){

        treeView.setEditable(true);
//        TreeItem<UserItem> parentItem = getParentItem();
//        TreeItem<UserItem> newItem = new TreeItem<>();
//        newItem.setValue(new UserItem(parentItem.getValue().getFile() + "\\",true, readTemporaryName(parentItem)));
//        parentItem.getChildren().add(0, newItem);
//        parentItem.setExpanded(true);
        treeView.requestFocus();
        treeView.getFocusModel().focus(0);
        treeView.layout();
        treeView.edit(item);


//        treeView.setEditable(false);
    }

    public void updateViewNew(MyDirectory myDirectory) {

//        System.out.println("Обновляемся");

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

    public void updateImageForExpanded(ObservableList<TreeItem<UserItem>> catalog) {
        for (TreeItem<UserItem> item : catalog) {
            if (item.getValue().isDir()){
                if (item.getChildren().size() > 0 && item.isExpanded()) {
                    item.setGraphic(item.isExpanded() ? new ImageView(ico.getIco("openCat")) : new ImageView(ico.getIco("cat")));
                    updateImageForExpanded(item.getChildren());
                } else {
                    item.setGraphic(new ImageView(ico.getIco("cat")));
                }
            }
        }
    }
}
