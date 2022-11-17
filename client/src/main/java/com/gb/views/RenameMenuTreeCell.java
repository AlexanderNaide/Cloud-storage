package com.gb.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.converter.DefaultStringConverter;

public class RenameMenuTreeCell extends TextFieldTreeCell<String> {
    private ContextMenu menu = new ContextMenu();

    public RenameMenuTreeCell() {
//        super(new DefaultStringConverter());

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