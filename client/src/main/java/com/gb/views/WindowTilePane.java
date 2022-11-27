package com.gb.views;

import com.gb.classes.MyDir.CloudCatalog;
import com.gb.classes.MyDir.MyDirectory;
import com.gb.classes.command.GetCatalog;
import com.gb.controllers.CloudWindowController;
import com.gb.views.ico.Ico;
import com.gb.views.ico.icoCatalog.IconLarge;
import com.gb.views.ico.icoCatalog.Large;
import com.gb.views.ico.icoCatalog.TileElement;
import com.gb.views.ico.icoDesktop.IcoDesktop;
import com.gb.views.ico.icoDesktop.InterfaceButtonBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.MAX_VALUE;
import static javafx.scene.layout.Region.*;

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
    @FXML
    public ButtonBar interfaceButton;

    @FXML
    public Label parentDirOnDisplay;

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

        parentDirOnDisplay = new Label();
        HBox.setHgrow(parentDirOnDisplay, Priority.ALWAYS);
        parentDirOnDisplay.setMinWidth(USE_PREF_SIZE);
        parentDirOnDisplay.setPrefWidth(USE_COMPUTED_SIZE);
        parentDirOnDisplay.setMaxWidth(MAX_VALUE);
        parentDirOnDisplay.setPadding(new Insets(0, 0, 0, 10));

        quickMenu.getChildren().add(0, parentDirOnDisplay);

        InterfaceButtonBar intButtonBar = new InterfaceButtonBar(interfaceButton, controller);

        workingWindow.setOnMouseClicked(event -> {
            if (event.getTarget() instanceof TilePane){
                for (Node child : workingWindow.getChildren()) {
                    ((TileElement) child).setFocus(false);
                }
            }
        });




    }


    public void windowCatalog(){
        Platform.runLater(() -> {
            loginWindow.setVisible(false);
            loginWindow.setMinHeight(0);
            loginWindow.setPrefHeight(0);
            loginWindow.setMaxHeight(0);

            quickMenu.setVisible(true);
            quickMenu.setMinHeight(20);
            quickMenu.setPrefHeight(20);
            quickMenu.setMaxHeight(20);

            interactiveWindow.setVisible(true);
            interactiveWindow.setMinHeight(60);
            interactiveWindow.setPrefHeight(60);
            interactiveWindow.setMaxHeight(60);

            workingWindow.setVisible(true);
            workingWindow.setMinHeight(USE_PREF_SIZE);
            workingWindow.setPrefHeight(USE_COMPUTED_SIZE);
            workingWindow.setMaxHeight(MAX_VALUE);

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
            quickMenu.setMaxHeight(0);

            interactiveWindow.setVisible(false);
            interactiveWindow.setMinHeight(0);
            interactiveWindow.setPrefHeight(0);
            interactiveWindow.setMaxHeight(0);

            workingWindow.setVisible(false);
            workingWindow.setMinHeight(0);
            workingWindow.setPrefHeight(0);
            workingWindow.setMaxHeight(0);

            loginWindow.setVisible(true);
            loginWindow.setMinHeight(USE_PREF_SIZE);
            loginWindow.setPrefHeight(USE_COMPUTED_SIZE);
            loginWindow.setMaxHeight(MAX_VALUE);
        });
    }

    public void getUp(){
        this.controller.sendMessages(new GetCatalog(this.currentDir.getParent() == null ? this.currentDir : new File(this.currentDir.getParent())));
    }

    protected String readTemporaryName(){
        String name = "Новая папка";
        String newName = name;
        boolean x = false;
        int n = 2;
        while (!x){
            for (Node node : workingWindow.getChildren()) {
                Large l = (Large) node;
                if (l.getFile().getName().equals(newName)){
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

    public void setEditing(TreeItem<UserItem> item){

        treeView.setEditable(true);
        treeView.requestFocus();
        treeView.getFocusModel().focus(0);
        treeView.layout();
        treeView.edit(item);
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
            parentDirOnDisplay.setText(currentDir.getPath().replace(currentDir.toPath().getName(0).toString(), "Home"));
            workingWindow.getChildren().remove(0, workingWindow.getChildren().size());
            workingWindow.getChildren().addAll(list);
        });
    }


    public void updateViewNew(MyDirectory myDirectory) {
        if (loginWindow.isVisible()){
            windowCatalog();
        }
    }
}
