package com.gb.classes.command;

import com.gb.classes.Command;

import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CatalogOld extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = -461092941148314084L;
    private final Map<Path, Path> catalog;

    public CatalogOld(){
        catalog = new HashMap<Path, Path>();
    }

    public void add(Path path, Path cat){
        catalog.put(path, cat);
    }

    public Map<Path, Path> getCatalog(){
        return catalog;
    }

    public void clear(){
        catalog.clear();
    }
}
