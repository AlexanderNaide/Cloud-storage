package com.gb.views;


import javafx.scene.image.Image;

import java.util.Objects;

public class IconVer1 implements Ico{
    private final Image home;
    private final Image file;
    private final Image saveFile;
    private final Image cat;
    private final Image openCat;


    public IconVer1() {
        home = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/home.png")));
        file = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/file.png")));
        saveFile = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/saveFile.png")));
        cat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/cat.png")));
        openCat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/openCat.png")));
    }

    @Override
    public Image getIco(String string) {
        return switch (string){
            case "home" -> home;
            case "file" -> file;
            case "saveFile" -> saveFile;
            case "cat" -> cat;
            case "openCat" -> openCat;
            default -> null;
        };
    }
}
