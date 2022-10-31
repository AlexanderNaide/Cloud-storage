package com.gb.views;

import java.io.File;

public class UserItem {
    private final File file;
    private File localFile;
    private final boolean isDir;

    public UserItem(File file, boolean isDir) {
        this.file = file;
        this.isDir = isDir;
    }

    @Override
    public String toString() {
        return file.getName();
    }

    public void setLocalFile(File file){
        this.localFile = file;
    }

    public File getFile() {
        return file;
    }

    public File getLocalFile() {
        return localFile;
    }

    public boolean isDir() {
        return isDir;
    }

/*    public Image getImage(boolean isExp){
        return new Image()
    }*/
}
