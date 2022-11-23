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
        up = new Image("/desk/up.png", 36, 36, false, false);
        add = new Image("/desk/add.png", 36, 36, false, false);
        del = new Image("/desk/del.png", 36, 36, false, false);
        download = new Image("/desk/down.png", 36, 36, false, false);
        upload = new Image("/desk/upload.png", 36, 36, false, false);

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
