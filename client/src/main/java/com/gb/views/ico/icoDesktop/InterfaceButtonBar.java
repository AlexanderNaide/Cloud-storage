package com.gb.views.ico.icoDesktop;

import com.gb.controllers.CloudWindowController;
import com.gb.views.ico.Ico;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.ImageView;

import java.io.IOException;

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
        interfaceButton.getButtons().add(0, up);
        up.setOnAction(event -> {
            controller.getUp();
        });

        Button add = new InterfaceButton("Создать папку", new ImageView(ico.getIco("add")));
        interfaceButton.getButtons().add(1, add);
        add.setOnAction(event -> {
            System.out.println("Создать папку");
        });

        Button upload = new InterfaceButton("Загрузить файл", new ImageView(ico.getIco("upload")));
        interfaceButton.getButtons().add(2, upload);
        upload.setOnAction(event -> {
            try {
                controller.AddFile(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button download = new InterfaceButton("Скачать файл", new ImageView(ico.getIco("download")));
        interfaceButton.getButtons().add(3, download);
        download.setOnAction(event -> {
            System.out.println("Скачать файл");
        });

        Button del = new InterfaceButton("Удалить с сервера", new ImageView(ico.getIco("del")));
        interfaceButton.getButtons().add(4, del);
        del.setOnAction(event -> {
            controller.DeleteButton(event);
            System.out.println("Удалить с сервера");
        });
    }
}
