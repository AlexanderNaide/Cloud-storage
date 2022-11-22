package com.gb.views;

import com.gb.classes.command.GetCatalog;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

import static com.gb.controllers.CloudWindowController.sendMessages;

public class Large extends VBox {

    private final File file;
    public Large(ImageView imageView, File file) {
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(imageView);
        this.file = file;
        Label label = new Label(file.getName());
        this.getChildren().add(label);
        this.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2){

                sendMessages(new GetCatalog(file));
            }
        });
    }

    public File getFile() {
        return file;
    }
}
