package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.File;
import java.io.Serial;

public class DeleteFile extends Command {
    @Serial
    private static final long serialVersionUID = 8016125878023400588L;

    public DeleteFile(File file) {
        this.name = "deleteFile";
        this.file = file;
    }

}
