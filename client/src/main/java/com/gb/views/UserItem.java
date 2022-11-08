package com.gb.views;

import java.io.File;

public class UserItem {

    private String owner = "user1";
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

    public void setFile(File file){
        if (this.file == null){
            this.file = file;
        }
    }

    @Override
    public String toString() {
        if (file != null && !file.getName().equals(owner)) {
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
