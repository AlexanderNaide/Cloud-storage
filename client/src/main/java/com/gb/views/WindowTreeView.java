package com.gb.views;

import com.gb.classes.MyDir.MyDirectory;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.File;
import java.util.LinkedList;

public class WindowTreeView {

    public VBox VBoxHomeWindow;
//    private final TreeView <String> treeView;
    private final TreeView <UserItem> treeView;
    TreeItem<UserItem> root;

    private LinkedList<File> list;

    private final Ico ico;

    private StringBuilder parentDir;
    private MyDirectory md;


    public WindowTreeView (VBox VBoxHomeWindow){
        this.VBoxHomeWindow = VBoxHomeWindow;
//        treeView = new TreeView<String>();
        treeView = new TreeView<UserItem>();
        this.VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        ico = new IconVer1();
//        root = new TreeItem<>("Home", new ImageView(ico.getIco("home")));
        UserItem rootItem = new UserItem(new File("Home"), true);
        root = new TreeItem<>(rootItem, new ImageView(ico.getIco("home")));
        treeView.setRoot(root);
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

                File file = new File(string);
                System.out.println(file);
                return new UserItem(file,true);
            }
        }));
        root.setExpanded(true);
        parentDir = new StringBuilder();

        initializeList();
    }
    private void initializeList() {

    }

    private String readDir (){

        return "";
    }


    public void setEditing(ActionEvent actionEvent){

        treeView.setEditable(true);
        TreeItem<UserItem> newItem = new TreeItem<>();
        newItem.setValue(new UserItem(new File("Item " + treeView.getExpandedItemCount()), true));
        TreeItem<UserItem> parentItem =  treeView.getSelectionModel().getSelectedItem();
        if (parentItem == null){
            parentItem = treeView.getRoot();
        } else if (!parentItem.getValue().isDir()) {
            parentItem = parentItem.getParent();
        }
        parentItem.getChildren().add(0, newItem);
        treeView.requestFocus();
        parentItem.setExpanded(true);
        treeView.getFocusModel().focus(0);
        treeView.layout();
        treeView.edit(newItem);
        treeView.setEditable(false);
    }

    public void updateViewNew(MyDirectory myDirectory) {
        md = myDirectory;
        TreeItem<UserItem> newUserCatalog = new TreeItem<>();
        newUserCatalog.getChildren().addAll(updateViewCat(md).getChildren());
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
                item.setGraphic(item.isExpanded() ? new ImageView(ico.getIco("openCat")) : new ImageView(ico.getIco("cat")));
                updateImageForExpanded(item.getChildren());
            }
        }
    }
}
