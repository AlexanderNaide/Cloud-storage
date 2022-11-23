package com.gb.views.ico.icoDesktop;

import com.gb.controllers.CloudWindowController;
import com.gb.views.ico.Ico;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.ImageView;

public class InterfaceButtonBar {

    private final ButtonBar interfaceButton;

    private CloudWindowController controller;
    private final Ico ico;

    public InterfaceButtonBar(ButtonBar interfaceButton, CloudWindowController controller) {
        this.interfaceButton = interfaceButton;
        this.controller = controller;
        this.ico = new IcoDesktop();
        init();
    }

    private void init(){
        Button up = new InterfaceButton("Назад", new ImageView(ico.getIco("up")));
        interfaceButton.getButtons().add(up);
        up.setOnAction(event -> {
            controller.getUp();
        });

        Button add = new InterfaceButton("Создать папку", new ImageView(ico.getIco("add")));
        interfaceButton.getButtons().add(add);
        add.setOnAction(event -> {
            controller.getUp();
        });

        Button upload = new InterfaceButton("Загрузить файл", new ImageView(ico.getIco("upload")));
        interfaceButton.getButtons().add(upload);

        Button download = new InterfaceButton("Скачать файл", new ImageView(ico.getIco("download")));
        interfaceButton.getButtons().add(download);

        Button del = new InterfaceButton("Удалить с сервера", new ImageView(ico.getIco("del")));
        interfaceButton.getButtons().add(del);
    }
}
