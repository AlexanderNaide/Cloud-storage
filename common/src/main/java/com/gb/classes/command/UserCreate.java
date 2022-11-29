package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.Serial;
import java.io.Serializable;

public class UserCreate extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 3252895155699679920L;
    private final String login;
    private final String password;

    public UserCreate(String login, String password) {
        this.name = "userCreate";
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
