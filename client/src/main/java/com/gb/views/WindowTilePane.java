package com.gb.views;

import com.gb.classes.MyDir.CloudCatalog;
import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.command.GetCatalog;
import com.gb.classes.command.NewCatalog;
import com.gb.classes.command.RenameFile;
import com.gb.controllers.CloudWindowController;
import com.gb.views.ico.Ico;
import com.gb.views.ico.icoCatalog.TileElement;
import com.gb.views.ico.icoDesktop.IcoDesktop;
import com.gb.views.ico.icoCatalog.IconLarge;
import com.gb.views.ico.icoCatalog.Large;
import com.gb.views.ico.icoDesktop.InterfaceButton;
import com.gb.views.ico.icoDesktop.InterfaceButtonBar;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WindowTilePane {

    @FXML
    protected Pane loginPane;
    protected CloudWindowController controller;
    public VBox VBoxHomeWindow;
    protected TreeView <UserItem> treeView;
    @FXML
    public ButtonBar buttonBar;
    @FXML
    public Hyperlink logOutPanel;
    @FXML
    public VBox loginWindow;
    @FXML
    public HBox interactiveWindow;
    @FXML
    public TilePane workingWindow;
    @FXML
    public HBox quickMenu;
//    @FXML
//    protected HBox desktopIcons;
    @FXML
    public ButtonBar interfaceButton;

    protected Ico ico;
    protected Ico desktopIco;
    protected File currentDir;
    List<Node> list;
    public void initialize(CloudWindowController controller) {

        this.controller = controller;
        windowLogin();
        ico = new IconLarge();
        desktopIco = new IcoDesktop();

        list = workingWindow.getChildren();

        InterfaceButtonBar intButtonBar = new InterfaceButtonBar(interfaceButton, controller);

        workingWindow.setOnMouseClicked(event -> {
            if (event.getTarget() instanceof TilePane){
                for (Node child : workingWindow.getChildren()) {
                    ((TileElement) child).setFocus(false);
                }
            }
        });







        treeView = new TreeView<UserItem>();
//        treeView.setVisible(false);
//        treeView.setShowRoot(false);  // скрывает корневой каталог
//        this.VBoxHomeWindow.getChildren().add(treeView);
//        VBox.setVgrow(treeView, Priority.ALWAYS);
//        treeView.setPadding(new Insets(5.0));
//        this.VBoxHomeWindow.getChildren().add(treeView);



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
                this.controller.sendMessages(new NewCatalog(userItem.getFile()));
            } else {
                this.controller.sendMessages(new RenameFile(userItem.isRename(), userItem.getFile()));
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


    public void windowCatalog(){
        Platform.runLater(() -> {
            quickMenu.setVisible(true);
            quickMenu.setMinHeight(20);
            quickMenu.setPrefHeight(20);

            interactiveWindow.setVisible(true);
            interactiveWindow.setMinHeight(60);
            interactiveWindow.setPrefHeight(60);

            loginWindow.setVisible(false);

            workingWindow.setVisible(true);
            workingWindow.setPrefHeight(800);
//            workingWindow.setPrefHeight(USE_COMPUTED_SIZE);
//            VBox.setMargin(workingWindow, new Insets(0));
//            workingWindow.setPadding(new Insets(10));
            workingWindow.setHgap(10);
            workingWindow.setVgap(10);
            workingWindow.setPrefTileHeight(120);
            workingWindow.setPrefTileWidth(90);
            workingWindow.setPadding(new Insets(10, 20, 10, 20));

        });
    }

    public void windowLogin(){

        Platform.runLater(() -> {
            quickMenu.setVisible(false);
            quickMenu.setMinHeight(0);
            quickMenu.setPrefHeight(0);

            interactiveWindow.setVisible(false);
            interactiveWindow.setMinHeight(0);
            interactiveWindow.setPrefHeight(0);

            workingWindow.setVisible(false);
            workingWindow.setPrefHeight(0);

            loginWindow.setVisible(true);
        });
    }

    public void getUp(){
        this.controller.sendMessages(new GetCatalog(this.currentDir.getParent() == null ? this.currentDir : new File(this.currentDir.getParent())));
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

    public List<TileElement> getSelected (){
        List<TileElement> list = new ArrayList<>();
        for (Node child : workingWindow.getChildren()) {
            TileElement element = (TileElement) child;
            if (element.isFocus()){
                list.add(element);
            }
        }
        return list;
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
        treeView.requestFocus();
        treeView.getFocusModel().focus(0);
        treeView.layout();
        treeView.edit(item);
//        treeView.setEditable(false);
    }


    public void updateCatalog(CloudCatalog command){
        if (loginWindow.isVisible()){
            windowCatalog();
        }
        currentDir = command.getCatalog();
        list = new ArrayList<>();
        for (File directory : command.getDirectories()) {
            list.add(new Large(new ImageView(ico.getIco("cat")), directory, controller::sendMessages));
        }
        for (File file : command.getFiles()) {
            list.add(new Large(new ImageView(ico.getIco("file")), file, controller::sendMessages));
        }
        Platform.runLater(() -> {
            workingWindow.getChildren().remove(0, workingWindow.getChildren().size());
            workingWindow.getChildren().addAll(list);
        });
    }


    public void updateViewNew(MyDirectory myDirectory) {
        if (loginWindow.isVisible()){
            windowCatalog();
        }

//        Platform.runLater(() -> {
//            workingWindow.getChildren().add(new Large(new ImageView(ico.getIco("cat")), "Catalog"));
//            list.add(new Large(new ImageView(ico.getIco("cat")), "Catalog2"));
//        });
//        workingWindow.getChildren().add(new Large(new ImageView(ico.getIco("cat")), "Catalog2"));


//        treeView.getRoot().getValue().setFile(myDirectory.getCatalog());
//        treeView.getRoot().getValue().setOwner(myDirectory.getCatalog().getName());
//
//        TreeItem<UserItem> newUserCatalog = new TreeItem<>(new UserItem(myDirectory.getCatalog(), true));
//        newUserCatalog.getChildren().addAll(updateViewCat(myDirectory).getChildren());
//        updateExpanded(newUserCatalog.getChildren(), treeView.getRoot().getChildren());
//        treeView.getRoot().getChildren().clear();
//        treeView.getRoot().getChildren().addAll(newUserCatalog.getChildren());
//
//        updateImageForExpanded(treeView.getRoot().getChildren());
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
