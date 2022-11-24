package com.gb.views.ico.icoDesktop;


import com.gb.views.ico.Ico;
import javafx.scene.image.Image;

public class IcoDesktop implements Ico {
    private final Image up;
    private final Image add;
    private final Image del;
    private final Image download;
    private final Image upload;


    public IcoDesktop() {
        int size = 30;
        up = new Image("/desk/up.png", size, size, false, false);
        add = new Image("/desk/add.png", size, size, false, false);
        del = new Image("/desk/del.png", size, size, false, false);
        download = new Image("/desk/down.png", size, size, false, false);
        upload = new Image("/desk/upload.png", size, size, false, false);

    }

    @Override
    public Image getIco(String string) {
        return switch (string){
            case "up" -> up;
            case "add" -> add;
            case "del" -> del;
            case "download" -> download;
            case "upload" -> upload;
            default -> null;
        };
    }
}
