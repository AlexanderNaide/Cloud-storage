package com.gb.views;

import java.io.File;

public class UserItem {
    private File file;
    private File localFile;
    private final boolean isDir;
    private String patch;

    private String temporaryName;

    public UserItem(File file, boolean isDir) {
        this.file = file;
        this.isDir = isDir;
    }

    public UserItem(String patch, boolean isDir, String temporaryName) {
        this.patch = patch;
        this.isDir = isDir;
        this.temporaryName = temporaryName;
    }

    public void generateFile(String name){
        this.file = new File(patch + name);
    }

    @Override
    public String toString() {
        if (file != null) {
            return file.getName();
        } else {
            if (temporaryName != null){
                return temporaryName;
            }
            return null;
        }
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
