package com.gb.views;


import javafx.scene.image.Image;

public class IconLarge implements Ico{
//    private final Image home;
    private final Image file;
//    private final Image saveFile;
    private final Image cat;
//    private final Image openCat;


    public IconLarge() {
//        home = new Image("/ico/home.png", 20, 20, false, false);
        file = new Image("/ico2/file.png", 60, 80, false, false);
//        saveFile = new Image("/ico/saveFile.png", 20, 20, false, false);
        cat = new Image("/ico2/cat.png", 60, 80, false, false);
//        openCat = new Image("/ico/openCat.png", 20, 20, false, false);
    }

    @Override
    public Image getIco(String string) {
        return switch (string){
//            case "home" -> home;
            case "file" -> file;
//            case "saveFile" -> saveFile;
            case "cat" -> cat;
//            case "openCat" -> openCat;
            default -> null;
        };
    }
}
