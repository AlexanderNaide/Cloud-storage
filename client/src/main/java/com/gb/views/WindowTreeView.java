package com.gb.views;

import com.gb.classes.MyDir.MyDirectory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;

public class WindowTreeView {

    public VBox VBoxHomeWindow;
    private final TreeView <String> treeView;
    TreeItem<String> root;

    private LinkedList<File> list;

    private IconVer1 ico;


    public WindowTreeView (VBox VBoxHomeWindow){
        this.VBoxHomeWindow = VBoxHomeWindow;
        treeView = new TreeView<String>();
        this.VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        ico = new IconVer1();
        root = new TreeItem<>("Home", new ImageView(ico.getHome()));
        treeView.setRoot(root);
        root.setExpanded(true);

        initializeList();

//        Button btn = new Button("Test");
//        this.VBoxHomeWindow.getChildren().add(btn);
//        VBox.setVgrow(btn, Priority.ALWAYS);
//        btn.setOnAction(ActionEvent event);
    }
    private void initializeList() {

    }

    public void updateView(LinkedList<File> newList){
        this.list = newList;
        ObservableList<TreeItem<String>> userCatalog = treeView.getRoot().getChildren();
        userCatalog.remove(0, userCatalog.size());

        list.forEach((f) -> {



            if(f.isDirectory()){
//                TreeItem<String> item = new TreeItem<>(f.getName());
                Path path = f.toPath();
                TreeItem<String> parentCat = treeView.getRoot();
                for (int i = 2; i < path.getNameCount(); i++) {
                    String catName = path.getName(i).toString();
                    System.out.println("Че там прочитали: " + catName);
//                    TreeItem<String> cat = null;
                    boolean isEmpty = false;
                    for (TreeItem<String> treeItem : parentCat.getChildren()) {
                        if (treeItem.getValue().equals(catName)){
                            System.out.println("Есть совпадение");
//                            cat = treeItem;
                            isEmpty= true;
                            parentCat = treeItem;
                            break;
                        }
                    }
                    if (!isEmpty){
                        TreeItem<String> newCat = new TreeItem<>(catName, new ImageView(ico.getCat()));
                        parentCat.getChildren().add(newCat);
                        parentCat = newCat;
                    }
                }
            } else {
                TreeItem<String> item = new TreeItem<>(f.getName(), new ImageView(ico.getFile()));
                Path path = f.toPath();
                TreeItem<String> parentCat = treeView.getRoot();
                for (int i = 2; i < path.getNameCount()-1; i++) {
                    String catName = path.getName(i).toString();
                    System.out.println("Че там прочитали: " + catName);
                    TreeItem<String> cat = null;
                    for (TreeItem<String> treeItem : parentCat.getChildren()) {
                        System.out.println("Вот это ----> " + treeItem.getValue());
                        if (treeItem.getValue().equals(catName)){
                            System.out.println("Есть совпадение");
                            cat = treeItem;
                            parentCat = treeItem;
                            break;
                        }
                    }
                    if (cat == null){
                        TreeItem<String> newCat = new TreeItem<>(catName);
                        parentCat.getChildren().add(newCat);
                        parentCat = newCat;
                    }

                }
                assert parentCat != null;
                parentCat.getChildren().add(item);
                System.out.println("Добавляем " + item + " в " + parentCat);
            }


 /*

                if(f.isDirectory()){
//                TreeItem<String> item = new TreeItem<>(f.getName());
                Path path = f.toPath();
                TreeItem<String> parentCat = treeView.getRoot();
                for (int i = 2; i < path.getNameCount(); i++) {
                    String catName = path.getName(i).toString();
                    System.out.println("Че там прочитали: " + catName);
//                    TreeItem<String> cat = null;
                    boolean isEmpty = false;
                    for (TreeItem<String> treeItem : parentCat.getChildren()) {
                        if (treeItem.getValue().equals(catName)){
                            System.out.println("Есть совпадение");
//                            cat = treeItem;
                            isEmpty= true;
                            parentCat = treeItem;
                            break;
                        }
                    }
                    if (!isEmpty){
                        TreeItem<String> newCat = new TreeItem<>(catName);
                        parentCat.getChildren().add(newCat);
                        parentCat = newCat;
                    }
                }
            } else {
                TreeItem<String> item = new TreeItem<>(f.getName());
                Path path = f.toPath();
                TreeItem<String> parentCat = null;
                for (int i = 1; i < path.getNameCount()-1; i++) {
                    if (i == 1){
                        parentCat = treeView.getRoot();
                        continue;
                    }
                    String catName = path.getName(i).toString();
                    System.out.println("Че там прочитали: " + catName);
                    TreeItem<String> cat = null;
                    for (TreeItem<String> treeItem : parentCat.getChildren()) {
                        System.out.println("Вот это ----> " + treeItem.getValue());
                        if (treeItem.getValue().equals(catName)){
                            System.out.println("Есть совпадение");
                            cat = treeItem;
                            parentCat = treeItem;
                            break;
                        }
                    }
                    if (cat == null){
                        TreeItem<String> newCat = new TreeItem<>(catName);
                        parentCat.getChildren().add(newCat);
                        parentCat = newCat;
                    }

                }
                assert parentCat != null;
                parentCat.getChildren().add(item);
                System.out.println("Добавляем " + item + " в " + parentCat);
            }

            */
        });


    }

    public String getParentItem(ActionEvent actionEvent){
        String s = treeView.getFocusModel().getFocusedItem().getValue();

        return s;
    }

    public void updateViewNew(MyDirectory md) {

        ObservableList<TreeItem<String>> userCatalog = treeView.getRoot().getChildren();
        ObservableList<TreeItem<String>> userCatalogCopy = userCatalog;
        userCatalog.remove(0, userCatalog.size());
        userCatalog.addAll(updateViewCat(md).getChildren());
    }

    public TreeItem<String> updateViewCat(MyDirectory md) {
        TreeItem<String> item = new TreeItem<>(md.getCatalog().getName(), new ImageView(ico.getCat()));
            for (File file : md.getFiles()) {
                item.getChildren().add(new TreeItem<>(file.getName(), new ImageView(ico.getFile())));
            }
            for (MyDirectory myDirectory : md.getDirectories()) {
                item.getChildren().add(updateViewCat(myDirectory));
            }
        return item;
    }
}
