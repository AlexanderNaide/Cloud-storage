package com.gb.views;

import com.gb.classes.MyDir.MyDirectory;
import com.gb.views.ico.Ico;
import com.gb.views.ico.icoCatalog.IconVer1;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.LinkedList;

public class WindowTreeViewOld {

    public VBox VBoxHomeWindow;
//    private final TreeView <String> treeView;
    private final TreeView <UserItem> treeView;
    TreeItem<UserItem> root;

    private LinkedList<File> list;

    private Ico ico;


    public WindowTreeViewOld(VBox VBoxHomeWindow){
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

    public File getParentItem(ActionEvent actionEvent){
        TreeItem<UserItem> item = treeView.getFocusModel().getFocusedItem();
        int pos = treeView.getFocusModel().getFocusedIndex();
        UserItem userItem = item.getValue();
//        TreeItem<UserItem> newItem = new TreeItem<>();
        if (userItem == null){
            Platform.runLater(() -> {
                treeView.getRoot().getChildren().add(0, new TreeItem<>());
            });

            return null;
        } else if (userItem.isDir()){
            return userItem.getFile();
        } else {
            return treeView.getFocusModel().getFocusedItem().getParent().getValue().getFile();
        }
    }

    public void setEditing(ActionEvent actionEvent){
//        int pos = treeView.getFocusModel().getFocusedIndex();
//        textField.setLayoutX(item.getGraphic().getLayoutX());
//        System.out.println(item.getGraphic().getLayoutBounds().getHeight());
//        textField.setLayoutY((pos + 2) * (item.getGraphic().getLayoutBounds().getHeight()));
        TreeItem<UserItem> item = treeView.getFocusModel().getFocusedItem();
        ObservableList <TreeItem<UserItem>> list = item.getParent().getChildren();
        TreeItem<UserItem> newItem = new TreeItem<>(new UserItem(new File(""), true));
        list.add(0, newItem);

        treeView.setEditable(true);
        treeView.requestFocus();
//        treeView.layout();
        treeView.getSelectionModel().select(newItem);
        treeView.edit(newItem);


//        PauseTransition p = new PauseTransition( Duration.millis( 100 ) );
//        p.setOnFinished(event -> treeView.edit(newItem));
//        p.play();



/*        treeView.setOnEditStart(new EventHandler<TreeView.EditEvent<UserItem>>() {
            @Override
            public void handle(TreeView.EditEvent<UserItem> event) {
                System.out.println("Start editing");
                System.out.println(event.getNewValue().toString());
            }
        });*/

    }

    public void updateViewNew(MyDirectory md) {
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
