package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.Serial;
import java.io.Serializable;

public class TestCommand extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 3298857156294092490L;

    public TestCommand(){
        this.name = "Test";
    }
}
