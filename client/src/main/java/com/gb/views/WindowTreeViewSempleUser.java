package com.gb.views;

import com.gb.views.ico.Ico;
import com.gb.views.ico.icoCatalog.IconVer1;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.File;
import java.util.LinkedList;

public class WindowTreeViewSempleUser {

    public VBox VBoxHomeWindow;
//    private final TreeView <String> treeView;
    private TreeView <UserItem> treeView;
    TreeItem<UserItem> root;

    private LinkedList<File> list;

    private Ico ico;


    public WindowTreeViewSempleUser(VBox VBoxHomeWindow){
        this.VBoxHomeWindow = VBoxHomeWindow;
        treeView = new TreeView<UserItem>();
        this.VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        ico = new IconVer1();

        treeView.setCellFactory(param -> new TextFieldTreeCell<UserItem>(new StringConverter<UserItem>() {
            @Override
            public String toString(UserItem object) {
                return object.toString();
            }

            @Override
            public UserItem fromString(String string) {
                treeView.requestFocus();
                return new UserItem(new File(string), true);
            }
        }));


        root = new TreeItem<>();
        root.setValue(new UserItem(new File("Home"), true));
        root.setExpanded(true);
        treeView.setRoot(root);

    }

    public void setEditing(ActionEvent actionEvent){

        treeView.setEditable(true);
        TreeItem<UserItem> newItem = new TreeItem<>();
        newItem.setValue(new UserItem(new File("Item " + treeView.getExpandedItemCount()), true));
        TreeItem<UserItem> parentItem =  treeView.getSelectionModel().getSelectedItem();
        if (parentItem == null){
            parentItem = treeView.getRoot();
        }
        parentItem.getChildren().add(newItem);
        treeView.requestFocus();
        treeView.getSelectionModel().select(newItem);
        treeView.layout();
        treeView.edit(newItem);
        treeView.setEditable(false);

    }
}
