package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Catalog extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = -461092941148314084L;
//    private final Map<File, File> catalog;

    private final LinkedList<File> list;
    public Catalog(){
//        catalog = new HashMap<File, File>();
        list = new LinkedList<>();
    }

    public void add(Path path){
        list.add(path.toFile());
    }

    public LinkedList<File> getCatalog(){
        return list;
    }

    public void clear(){
        list.clear();
    }
}
