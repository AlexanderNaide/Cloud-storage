package com.gb.classes;

import java.util.HashMap;
import java.util.Map;

public class Catalog {
    private final Map<File, File> catalog;

    public Catalog(){
        catalog = new HashMap<File, File>();
    }

    public void add(File file, File cat){
        catalog.put(file, cat);
    }

    public Map<File, File> getCatalog(){
        return catalog;
    }

    public void clear(){
        catalog.clear();
    }
}
