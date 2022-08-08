package com.github.methodia.minibilling;

import java.io.File;

public class FolderGenerator {
    private User user;
//    private String folderPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\newFoldersForUsers\\";
    private String folderPath;

    public FolderGenerator(User user, String folderPath) {
        this.user = user;
        this.folderPath = folderPath;
    }

    public String folderGenerate(){
        User userForFolder = user;
        String name = userForFolder.getName();
        String refNum = userForFolder.getRef();
        String nameForFolder = folderPath + name + "-" + refNum;
        File newFolder = new File(nameForFolder);
        boolean bool2 = newFolder.mkdirs();
        return nameForFolder;
    }
}
