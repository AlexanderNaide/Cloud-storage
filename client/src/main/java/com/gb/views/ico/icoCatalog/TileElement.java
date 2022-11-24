package com.gb.views.ico.icoCatalog;

import com.gb.classes.command.GetCatalog;
import com.gb.net.MessageReceived;
import javafx.scene.layout.VBox;
import java.io.File;


public class TileElement extends VBox {
    protected final File file;
    private final MessageReceived received;
    public TileElement(File file, MessageReceived received) {
        this.file = file;
        this.received = received;
        this.setOnMouseClicked(event -> {
            this.requestFocus();
            this.setFocused(true);
            if (event.getClickCount() == 2){
                this.received.onReceived(new GetCatalog(this.file));
            }
            System.out.println(file.getName() + " " + this.isFocused());
        });
    }

    public File getFile() {
        return file;
    }
}
