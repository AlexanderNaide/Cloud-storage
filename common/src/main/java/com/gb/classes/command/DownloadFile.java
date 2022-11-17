package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.File;
import java.io.Serial;

public class DownloadFile extends Command {

    @Serial
    private static final long serialVersionUID = 6296599918695861688L;
    private final byte[] dataByte;

    public DownloadFile(File file, byte[] dataByte) {
        this.name = "downloadFile";
        this.file = file;
        this.dataByte = dataByte;
    }

    public byte[] getDataByte(){
        return dataByte;
    }

}
