package com.gb.views;


import javafx.scene.image.Image;

public class IcoDesctop implements Ico{
    private final Image up;
//    private final Image home;
//    private final Image saveFile;
//    private final Image cat;
//    private final Image openCat;


    public IcoDesctop() {
        up = new Image("/ico2/up.png", 36, 36, false, false);
//        home = new Image("/ico/home.png", 20, 20, false, false);
//        saveFile = new Image("/ico/saveFile.png", 20, 20, false, false);
//        cat = new Image("/ico2/cat.png", 60, 80, false, false);
//        openCat = new Image("/ico/openCat.png", 20, 20, false, false);
    }

    @Override
    public Image getIco(String string) {
        return switch (string){
            case "up" -> up;
//            case "home" -> home;
//            case "saveFile" -> saveFile;
//            case "cat" -> cat;
//            case "openCat" -> openCat;
            default -> null;
        };
    }
}
