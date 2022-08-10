package com.github.methodia.minibilling;

import java.io.File;

public class FolderGenerator {

    public String generate(User user, String folderPathStr) {
        String folderPath = folderPathStr;
        User consumer = user;
        String name = consumer.getName();
        String refNum = consumer.getRef();
        String pathname = folderPath + name + "-" + refNum;
        File newFolder = new File(pathname);
        boolean bool2 = newFolder.mkdirs();
        return pathname;
    }
}
