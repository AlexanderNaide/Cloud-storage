package com.gb.classes.MyDir;

import com.gb.classes.Command;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.Serial;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

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

    public MyDirectory(String root, String file) throws NotDirectoryException {

        this.name = "myDirectory";
        this.catalogName = new File(file);
        this.files = new ArrayList<>();
        this.directories = new ArrayList<>();
        updateDirectory(root, file);
    }

/*    public MyDirectory(File file) throws NotDirectoryException {
        this.name = "myDirectory";

        this.catalogName = (Path.of(file.getParent()).relativize(file.toPath())).toFile();
//        this.catalogName = (Path.of(file.getParent()).relativize(file.toPath())).toFile();
        this.files = new ArrayList<>();
        this.directories = new ArrayList<>();
        System.out.println("file - " + file + "||| catalogName - " + catalogName);
        updateDirectory(file);
    }*/

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
                    directories.add(new MyDirectory(root, (of.relativize(f.toPath())).toString()));
                }
            }
        } else {
            throw new NotDirectoryException();
        }
    }
}
