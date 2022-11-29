package com.gb.views.ico.icoDesktop;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class InterfaceButton extends Button {

    public InterfaceButton(String text, Node graphic) {
        super("", graphic);
        this.setGraphic(graphic);
        this.getStyleClass().add("interface-button");
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(new Duration(300));
        this.setTooltip(tooltip);

        this.setPadding(new Insets(12, 0, 12, 0));
        this.setPrefSize(52, 52);
    }
}
