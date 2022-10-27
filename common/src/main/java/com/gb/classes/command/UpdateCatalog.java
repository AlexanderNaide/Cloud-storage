package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.Serial;
import java.io.Serializable;

public class UpdateCatalog extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = -677292597669016975L;

    public UpdateCatalog(){
        this.name = "UpdateCatalog";
    }
}
