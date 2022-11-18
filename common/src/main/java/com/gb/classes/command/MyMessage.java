package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.Serial;
import java.io.Serializable;

public class MyMessage extends Command implements Serializable {

    @Serial
    private static final long serialVersionUID = -5780983606448729868L;
    private final String text;

    public MyMessage(String text) {
        this.name = "message";
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
