package com.gb.classes.MyDir;

import java.io.Serial;

public class NotDirectoryException extends Exception{
    @Serial
    private static final long serialVersionUID = -1003373528060393510L;

    public NotDirectoryException() {
        super("This file is not directory.");
    }
}
