package com.gb.views.ico.icoCatalog;

import com.gb.classes.command.NewCatalog;
import com.gb.classes.command.RenameFile;
import com.gb.net.MessageReceived;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;

public class Large extends TileElement {

    public Large(ImageView imageView, File file, MessageReceived received) {
        super(file, received);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(imageView);
        String fileName = file.getName();
        Label label = new Label(fileName);
        label.setLineSpacing(2);
        label.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(label);
        this.getStyleClass().addAll("large");
        this.setFillWidth(false);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(0, 5, 0, 5));

        Tooltip tooltip = new Tooltip(file.getName());
        tooltip.setShowDelay(new Duration(300));
        label.setTooltip(tooltip);
        
    }

    public void editing(){
        TextField textField = new TextField(file.getName());
        this.getChildren().remove(1);
        this.getChildren().add(1, textField);
        textField.selectAll();
        textField.requestFocus();
        textField.setOnAction(actionEvent -> {
            String newName = textField.getText();
            textField.setVisible(false);
            this.getChildren().remove(1);
            File newFile = new File(file.getParent()  + "\\" + newName);
            super.setFile(newFile);
            this.getChildren().add(1, new Label(file.getName()));
            received.onReceived(new NewCatalog(newFile));
        });
    }

    public void rename(){
        TextField textField = new TextField(file.getName());
        this.getChildren().remove(1);
        this.getChildren().add(1, textField);
        textField.selectAll();
        textField.requestFocus();
        textField.setOnAction(actionEvent -> {
            String newName = textField.getText();
            textField.setVisible(false);
            this.getChildren().remove(1);
            File oldFile = file;
            File newFile = new File(file.getParent()  + "\\" + newName);
            super.setFile(newFile);
            this.getChildren().add(1, new Label(file.getName()));
            received.onReceived(new RenameFile(oldFile, newFile));
        });
    }
}
