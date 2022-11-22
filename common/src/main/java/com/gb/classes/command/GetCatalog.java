package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.File;
import java.io.Serial;

public class GetCatalog extends Command {

    @Serial
    private static final long serialVersionUID = -1445625023669445126L;

    public GetCatalog(File file) {
        this.name = "getCatalog";
        this.file = file;
    }
}
