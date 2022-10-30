package com.gb.classes.MyDir;

import com.gb.classes.Command;

import java.io.File;
import java.io.Serial;
import java.util.ArrayList;

public class MyDirectory extends Command {
    @Serial
    private static final long serialVersionUID = 4642337864560296723L;
    private final File catalogName;
    private final ArrayList<File> files;
    private final ArrayList<MyDirectory> directories;

    public File getCatalog() {
        return catalogName;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public ArrayList<MyDirectory> getDirectories() {
        return directories;
    }

    public MyDirectory(File file) throws NotDirectoryException {
        this.catalogName = file;
        this.files = new ArrayList<>();
        this.directories = new ArrayList<>();
        updateDirectory(file);
    }

    public void updateDirectory(File file) throws NotDirectoryException {
        if (file.isDirectory()){
            File[] ch = file.listFiles();
            assert ch != null;
            for (File f : ch){
                if(f.isFile()){
                    files.add(f);
                } else {
                    directories.add(new MyDirectory(f));
                }
            }
        } else {
            throw new NotDirectoryException();
        }
    }
}
