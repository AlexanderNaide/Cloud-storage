package com.gb.classes;

import java.io.Serial;
import java.io.Serializable;

public abstract class Command implements Serializable {
    @Serial
    private static final long serialVersionUID = -967509272087472154L;
    protected String name;
    public String getName(){
        return name;
    }
}
