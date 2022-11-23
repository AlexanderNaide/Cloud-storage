package com.gb.views.ico.icoDesktop;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class InterfaceButton extends Button {

    public InterfaceButton(String text, Node graphic) {
        super("", graphic);
        this.setGraphic(graphic);
        this.getStyleClass().add("interface-button");
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(new Duration(300));
        this.setTooltip(tooltip);

//        this.paddingProperty().set(new Insets(3));
        this.setPadding(new Insets(6, 0, 6, 0));
//        this.setPrefSize(52, 52);
//        this.setMaxSize(52, 52);
//        this.setMinSize(52, 52);
    }
}
