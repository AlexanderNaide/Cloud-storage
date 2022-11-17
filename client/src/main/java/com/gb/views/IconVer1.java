package com.gb.views;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class IconVer1 implements Ico{
    private final Image home;
    private final Image file;
    private final Image saveFile;
    private final Image cat;
    private final Image openCat;


    public IconVer1() {
        home = new Image("/ico/home.png", 20, 20, false, false);
//        home = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/home.png")));
        file = new Image("/ico/file.png", 20, 20, false, false);
//        file = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/file.png")));
        saveFile = new Image("/ico/saveFile.png", 20, 20, false, false);
//        saveFile = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/saveFile.png")));
        cat = new Image("/ico/cat.png", 20, 20, false, false);
//        cat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/cat.png")));
        openCat = new Image("/ico/openCat.png", 20, 20, false, false);
//        openCat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/openCat.png")));
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
