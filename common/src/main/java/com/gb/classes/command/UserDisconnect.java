package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.Serial;
import java.io.Serializable;

public class UserDisconnect extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 3252895155699679920L;

    public UserDisconnect() {
        this.name = "userDisconnect";
    }
}
