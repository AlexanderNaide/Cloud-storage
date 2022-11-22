package com.gb.views;

import com.gb.classes.command.GetCatalog;
import com.gb.net.MessageReceived;
import javafx.scene.layout.VBox;
import java.io.File;

//import static com.gb.controllers.CloudWindowController.sendMessages;


public class TileElement extends VBox {
    protected final File file;
    private final MessageReceived received;
    public TileElement(File file, MessageReceived received) {
        this.file = file;
        this.received = received;
        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                this.received.onReceived(new GetCatalog(this.file));
            }
        });
    }

    public File getFile() {
        return file;
    }
}
