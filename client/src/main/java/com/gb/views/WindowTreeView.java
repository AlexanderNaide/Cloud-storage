package com.gb.views;

import com.gb.classes.MyDir.MyDirectory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;

public class WindowTreeView {

    public VBox VBoxHomeWindow;
//    private final TreeView <String> treeView;
    private final TreeView <UserItem> treeView;
    TreeItem<UserItem> root;

    private LinkedList<File> list;

    private Ico ico;


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
        root.setExpanded(true);

        initializeList();

//        Button btn = new Button("Test");
//        this.VBoxHomeWindow.getChildren().add(btn);
//        VBox.setVgrow(btn, Priority.ALWAYS);
//        btn.setOnAction(ActionEvent event);
    }
    private void initializeList() {

    }

/*    public void updateView(LinkedList<File> newList){
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
                        TreeItem<String> newCat = new TreeItem<>(catName, new ImageView(ico.getIco("cat")));
                        parentCat.getChildren().add(newCat);
                        parentCat = newCat;
                    }
                }
            } else {
                TreeItem<String> item = new TreeItem<>(f.getName(), new ImageView(ico.getIco("file")));
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
        });


    }*/

    public String getParentItem(ActionEvent actionEvent){
        UserItem item = treeView.getFocusModel().getFocusedItem().getValue();
        if (item == null){
            return treeView.getRoot().getValue().toString();
        } else if (item.isDir()){
            return item.toString();
        } else {
            return treeView.getFocusModel().getFocusedItem().getParent().getValue().toString();
        }
    }

    public void updateViewNew(MyDirectory md) {
        TreeItem<UserItem> newUserCatalog = new TreeItem<>();
        newUserCatalog.getChildren().addAll(updateViewCat(md).getChildren());
        updateExpanded(newUserCatalog.getChildren(), treeView.getRoot().getChildren());
        treeView.getRoot().getChildren().clear();
        treeView.getRoot().getChildren().addAll(newUserCatalog.getChildren());
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
}
