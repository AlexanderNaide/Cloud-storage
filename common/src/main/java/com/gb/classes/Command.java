package com.gb.classes;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

public abstract class Command implements Serializable {
    @Serial
    private static final long serialVersionUID = -967509272087472154L;
    protected String name;
    protected File file;
    public String getName(){
        return name;
    }
    public File getFile(){
        return file;
    }
}
