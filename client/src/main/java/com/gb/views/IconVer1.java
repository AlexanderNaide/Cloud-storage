package com.gb.views;


import javafx.scene.image.Image;

import java.util.Objects;

public class IconVer1 {
    private final Image home;
    private final Image file;
    private final Image saveFile;
    private final Image cat;


    public IconVer1() {
        home = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/home.png")));
        file = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/file.png")));
        saveFile = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/saveFile.png")));
        cat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/cat.png")));
    }

    public Image getCat() {
        return cat;
    }

    public Image getHome() {
        return home;
    }

    public Image getFile() {
        return file;
    }

    public Image getSaveFile() {
        return saveFile;
    }
}
