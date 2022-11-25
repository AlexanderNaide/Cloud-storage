package com.gb.views.ico.icoCatalog;

import com.gb.classes.command.GetCatalog;
import com.gb.net.MessageReceived;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.io.File;


public class TileElement extends VBox {
    protected final File file;
    protected BooleanProperty focus;
    private final MessageReceived received;
    public TileElement(File file, MessageReceived received) {
        this.file = file;
        this.received = received;
        this.focus = new SimpleBooleanProperty(false);
        getStyleClass().add("tile-element");
        focus.addListener(e -> {
            pseudoClassStateChanged(PseudoClass.getPseudoClass("focus"), focus.get());
        });

        this.setOnMouseClicked(event -> {
            for (Node node : this.getParent().getChildrenUnmodifiable()) {
                ((TileElement) node).setFocus(false);
            }
//            this.requestFocus();
            setFocus(!isFocus());
            if (event.getClickCount() == 2){
                this.received.onReceived(new GetCatalog(this.file));
            }
//            System.out.println(file.getName() + " " + this.isFocused());
        });
    }

    public File getFile() {
        return file;
    }

    public boolean isFocus() {
        return focus.get();
    }

    public BooleanProperty focusProperty() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus.set(focus);
    }
}
