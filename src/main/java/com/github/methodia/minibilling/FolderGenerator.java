package com.github.methodia.minibilling;

import java.io.File;

public class FolderGenerator {

    public String generate(User user, String folderPathStr) {

        String name = user.getName();
        String refNum = user.getRef();
        String pathname = folderPathStr + name + "-" + refNum;
        File newFolder = new File(pathname);
        newFolder.mkdirs();
        return pathname;
    }
}
