package com.github.methodia.minibilling;

import java.io.File;

public class FolderGenerator {
    private User user;

    private String folderPathStr;

    public FolderGenerator(User user,String folderPath) {
        this.user = user;
        this.folderPathStr=folderPath;
    }

    public String generate() {
        String folderPath=folderPathStr;
        User consumer = user;
        String name = consumer.getName();
        String refNum = consumer.getRef();
        String pathname = folderPath + name + "-" + refNum;
        File newFolder = new File(pathname);
        boolean bool2 = newFolder.mkdirs();
        return pathname;
    }
}
