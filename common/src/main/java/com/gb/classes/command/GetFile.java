package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.File;
import java.io.Serial;

public class GetFile extends Command {


    @Serial
    private static final long serialVersionUID = -4973701387401500008L;

    private String targetDir;

    public GetFile(File file, String targetDir) {
        this.name = "getFile";
        this.file = file;
        this.targetDir = targetDir;
    }

    public String getTargetDir() {
        return targetDir;
    }
}
