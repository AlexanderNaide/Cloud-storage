package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.Serial;

public class NewCatalog extends Command {
    @Serial
    private static final long serialVersionUID = 8016125878023400588L;
    private final String name;

    public NewCatalog(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
