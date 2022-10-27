package com.gb.views;

import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.*;

import java.nio.file.Path;
import java.util.Map;

public class WindowTreeView {

    public VBox VBoxHomeWindow;
    private final TreeView <String> treeView;
    TreeItem<String> root;


    public WindowTreeView (VBox VBoxHomeWindow){
        this.VBoxHomeWindow = VBoxHomeWindow;
        treeView = new TreeView<String>();
        VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        root = new TreeItem<>("Home");
        treeView.setRoot(root);
        root.setExpanded(true);

        initializeList();

        addTestItems();
    }
    private void initializeList() {

    }

    private void addTestItems() {

        TreeItem<String> Category1 = new TreeItem<>("Category 1");
        treeView.getRoot().getChildren().add(Category1);
        TreeItem<String> Category2 = new TreeItem<>("Category 2");
        treeView.getRoot().getChildren().add(Category2);
        TreeItem<String> Category3 = new TreeItem<>("Category 3");
        treeView.getRoot().getChildren().add(Category3);

        treeView.getRoot().getChildren().get(0).getChildren().add(new TreeItem<>("File 1"));
        treeView.getRoot().getChildren().get(0).getChildren().add(new TreeItem<>("File 2"));
        treeView.getRoot().getChildren().get(0).getChildren().add(new TreeItem<>("File 3"));

        treeView.getRoot().getChildren().get(1).getChildren().add(new TreeItem<>("File 1"));
        treeView.getRoot().getChildren().get(1).getChildren().add(new TreeItem<>("File 2"));

        treeView.getRoot().getChildren().get(2).getChildren().add(new TreeItem<>("File 1"));
        treeView.getRoot().getChildren().get(2).getChildren().add(new TreeItem<>("File 2"));
        TreeItem<String> Category4 = new TreeItem<>("Category 4");
        Category4.getChildren().add(new TreeItem<>("File 1"));
        Category4.getChildren().add(new TreeItem<>("File 2"));
        treeView.getRoot().getChildren().get(2).getChildren().add(Category4);


    }

    public void updateView(Map<Path, Path> map){
        System.out.println("jhbjvshbvdjsdhbvjshbv");
    }

}
