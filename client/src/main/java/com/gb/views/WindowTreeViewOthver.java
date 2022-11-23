package com.gb.views;

import com.gb.views.ico.Ico;
import com.gb.views.ico.icoCatalog.IconVer1;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

import java.io.File;
import java.util.LinkedList;

public class WindowTreeViewOthver {

    public VBox VBoxHomeWindow;
//    private final TreeView <String> treeView;
    private TreeView <String> treeView;
    TreeItem<String> root;

    private LinkedList<File> list;

    private Ico ico;


    public WindowTreeViewOthver(VBox VBoxHomeWindow){
        this.VBoxHomeWindow = VBoxHomeWindow;
//        treeView = new TreeView<String>();
        treeView = new TreeView<String>();
        this.VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        ico = new IconVer1();
//        root = new TreeItem<>("Home", new ImageView(ico.getIco("home")));
//        UserItem rootItem = new UserItem(new File("Home"), true);
//        root = new TreeItem<>(rootItem, new ImageView(ico.getIco("home")));





        root = new TreeItem<String> ("Tree");
        treeView.setRoot(root);
        root.setExpanded(true);
//        root.setRoot(root);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("Item" + i);
            root.getChildren().add(item);
        }

        treeView.setEditable(true);

        treeView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
//                treeView.setCellFactory(p -> new RenameMenuTreeCell2());

                TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
                System.out.println(item.getValue());
//                item.setValue("Suka");
//                treeView.setEditable(true);
//                treeView.edit(item);
            }
        });

        treeView.setCellFactory(p -> {
            return new RenameMenuTreeCell2();
        });

//        treeView.setCellFactory(p -> new RenameMenuTreeCell2());
//        StackPane root = new StackPane();
//        root.getChildren().add(treeView);
    }

    public void setEditing(ActionEvent actionEvent){
//        treeView.setOnEditStart(new EventHandler<TreeView.EditEvent<String>>() {
//            @Override
//            public void handle(TreeView.EditEvent<String> event) {
//                System.out.println(event);
//            }
//        });

        treeView.requestFocus();
        TreeItem<String> s = treeView.getSelectionModel().getSelectedItem();

        System.out.println(s);

        System.out.println(treeView.editingItemProperty().get());

        treeView.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new RenameMenuTreeCell2();
            }
        });
    }


    private static class RenameM extends TreeCell<String> {
        private TreeCell<String> yyy = new TreeCell<>();

        public RenameM() {
            MenuItem renameItem = new MenuItem("Rename");

            renameItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    startEdit();
                }
            });
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
                setText(null);
            }
        }
    }




    private static class RenameMenuTreeCell2 extends TextFieldTreeCell<String> {
        private final ContextMenu menu = new ContextMenu();

        public RenameMenuTreeCell2() {
            super(new DefaultStringConverter());
            MenuItem renameItem = new MenuItem("Rename");
            menu.getItems().add(renameItem);
            renameItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    startEdit();
                }
            });
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEditing()) {
                setContextMenu(menu);
            }
        }
    }
}
