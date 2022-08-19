package com.github.methodia.minibilling;

import java.io.File;

public class FolderGenerator {

    private final User user;
    private final String folderPath;

    public FolderGenerator(final User user, final String folderPath) {
        this.user = user;
        this.folderPath = folderPath;
    }

    public String folderGenerate() {
        final User userForFolder = user;
        final String name = userForFolder.getName();
        final String refNum = userForFolder.getRef();
        final String nameForFolder = folderPath + name + "-" + refNum;
        final File newFolder = new File(nameForFolder);
        final boolean bool2 = newFolder.mkdirs();
        return nameForFolder;
    }
}
