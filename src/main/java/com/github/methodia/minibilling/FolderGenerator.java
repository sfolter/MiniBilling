package com.github.methodia.minibilling;

import java.io.File;

public class FolderGenerator {
    private User user;
    private String folderPath = "C:\\Users\\Acer\\Desktop\\MiniBilling1\\src\\test\\resources\\sample1\\newFoldersForUsers\\";

    public FolderGenerator(User user) {
        this.user = user;
    }

    public String folderGenerate(){
        User userForFolder = user;
        String name = userForFolder.getName();
        String refNum = userForFolder.getRef();
        String nameForFolder = name + "-" + refNum;
        File newFolder = new File(nameForFolder);
        boolean bool2 = newFolder.mkdirs();
        return nameForFolder;
    }
}
