package com.gb.classes;

public class File {
    private String name;
    private final boolean isDirectory;
    private String address;

    public File(String name, boolean isDirectory) {
        this.name = name;
        this.isDirectory = isDirectory;
    }

    public File(String name, boolean isDirectory, String address) {
        this(name, isDirectory);
        this.address = address;
    }

    public String getName(){
        return name;
    }
    public boolean isDirectory(){
        return isDirectory;
    }
    public String getAddress(){
        return address;
    }
    public void setNewName(String newName){
        this.name = newName;
    }
    public void setNewAddress(String newAddress){
        this.address = newAddress;
    }
}
