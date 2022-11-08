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
//    private final TreeView <String> treeView;
    private TreeView <UserItem> treeView;

    private LinkedList<File> list;

    private Ico ico;

    private StringBuilder parentDir;
    private MyDirectory md;
    public void initialize(CloudWindowController controller) {

        this.controller = controller;
//        assert false;
//        this.VBoxHomeWindow = VBoxHomeWindow;
//        treeView = new TreeView<String>();
        treeView = new TreeView<UserItem>();
        this.VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        ico = new IconVer1();
//        root = new TreeItem<>("Home", new ImageView(ico.getIco("home")));
        UserItem rootItem = new UserItem(new File("Home"), true);
        TreeItem<UserItem> root = new TreeItem<>(rootItem, new ImageView(ico.getIco("home")));
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
                UserItem userItem = param.getEditingItem().getValue();
                userItem.generateFile(string);
                controller.sendMessages(new NewCatalog(userItem.getFile()));
                return userItem;
            }
        }));
        root.setExpanded(true);
        parentDir = new StringBuilder();

    }

    /*public WindowTreeView (VBox VBoxHomeWindow){
//        this.controller = controller;
//        assert false;
        this.VBoxHomeWindow = VBoxHomeWindow;
//        treeView = new TreeView<String>();
        treeView = new TreeView<UserItem>();
        this.VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        ico = new IconVer1();
//        root = new TreeItem<>("Home", new ImageView(ico.getIco("home")));
        UserItem rootItem = new UserItem(new File("Home"), true);
        TreeItem<UserItem> root = new TreeItem<>(rootItem, new ImageView(ico.getIco("home")));
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
                UserItem userItem = param.getEditingItem().getValue();
                userItem.generateFile(string);
                controller.sendMessages(new NewCatalog(userItem.getFile()));
                return userItem;
            }
        }));
        root.setExpanded(true);
        parentDir = new StringBuilder();
    }*/

/*    private String readDir (TreeItem<UserItem> item){
        StringBuilder sb = new StringBuilder();
        sb.append("\\");
        sb.insert(0, item.getValue().getFile());
        System.out.println("readDir - " + sb);
        return sb.toString();
    }*/

        private String readTemporaryName (TreeItem<UserItem> item){
            String name = "Новая папка";
            String newName = name;
            boolean x = false;
            int n = 1;
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
//        newItem.setValue(new UserItem(new File("Item " + treeView.getExpandedItemCount()), true));
        TreeItem<UserItem> parentItem =  treeView.getSelectionModel().getSelectedItem();
//        newItem.setValue(new UserItem(new File("parent","Item " + treeView.getExpandedItemCount()), true));
        if (parentItem == null){
            parentItem = treeView.getRoot();
        } else if (!parentItem.getValue().isDir()) {
            parentItem = parentItem.getParent();
        }
//        newItem.setValue(new UserItem(readDir(parentItem),true));

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
