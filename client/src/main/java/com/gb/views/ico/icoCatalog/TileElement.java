package com.gb.views.ico.icoCatalog;

import com.gb.classes.command.GetCatalog;
import com.gb.net.MessageReceived;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.io.File;


public abstract class TileElement extends VBox {
    protected File file;
    protected BooleanProperty focus;
    protected final MessageReceived received;
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
            setFocus(!isFocus());
            if (event.getClickCount() == 2){
                this.received.onReceived(new GetCatalog(this.file));
            }
        });
    }

    public abstract void editing();
    public abstract void rename();

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

    public void setFile(File file) {
        this.file = file;
    }
}
