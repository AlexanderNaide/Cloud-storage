package com.gb.classes.MyDir;

import com.gb.classes.Command;

import java.io.File;
import java.io.Serial;
import java.nio.file.Path;
import java.util.ArrayList;

public class CloudCatalog extends Command {

    @Serial
    private static final long serialVersionUID = -218920905206572908L;
    private final File catalogName;
    private final ArrayList<File> files;
    private final ArrayList<File> directories;

    public File getCatalog() {
        return catalogName;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public ArrayList<File> getDirectories() {
        return directories;
    }

    public CloudCatalog(String root, String file) throws NotDirectoryException {

        this.name = "cloudCatalog";
        this.catalogName = new File(file);
        this.files = new ArrayList<>();
        this.directories = new ArrayList<>();
        updateDirectory(root, file);
    }

    public void updateDirectory(String root, String file) throws NotDirectoryException {
        File dir;
        if ((dir = new File(root + file)).isDirectory()){
            File[] ch = dir.listFiles();
            assert ch != null;
            for (File f : ch){
                Path of = Path.of(root);
                if(f.isFile()){
                    files.add((of.relativize(f.toPath())).toFile());
                } else {
                    directories.add((of.relativize(f.toPath())).toFile());
                }
            }
        } else {
            throw new NotDirectoryException();
        }
    }
}
