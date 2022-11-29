package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.Serial;
import java.io.Serializable;

public class UserConnect extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 3252895155699679920L;
    private final String login;
    private final String password;

    public UserConnect(String login, String password) {
        this.name = "userConnect";
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
