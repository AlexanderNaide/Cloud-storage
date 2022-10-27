package com.gb.classes;

import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class Resource implements Serializable {

    @Serial
    private static final long serialVersionUID = -7378461789058679234L;
    private final Path path;
    private final boolean isDirectory;

    public Resource(Path path, boolean isDirectory) {
        this.path = path;
        this.isDirectory = isDirectory;
    }

    public String getName(){
        return path.getFileName().toString();
    }
    public boolean isDirectory(){
        return Files.isDirectory(path);
    }
}
