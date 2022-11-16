package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.File;
import java.io.Serial;

public class RenameFile extends Command {

    @Serial
    private static final long serialVersionUID = 3105575375827137111L;
    private File newFile;

    public RenameFile(File file, File newFile) {
        this.name = "renameFile";
        this.file = file;
        this.newFile = newFile;
    }

    public File getNewFile() {
        return newFile;
    }
}
