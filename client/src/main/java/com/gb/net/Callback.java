package com.gb.net;

import com.gb.classes.Command;

public interface Callback {
    void onReceive(Command com);
}
