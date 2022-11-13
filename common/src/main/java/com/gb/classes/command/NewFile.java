package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.File;
import java.io.Serial;

public class NewFile extends Command {
    @Serial
    private static final long serialVersionUID = 8016125878023400588L;
    private final byte[] dataByte;

    public NewFile(File file, byte[] dataByte) {
        this.name = "newFile";
        this.file = file;
        this.dataByte = dataByte;
    }

    public byte[] getDataByte(){
        return dataByte;
    }

}
