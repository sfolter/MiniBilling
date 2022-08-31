package com.github.methodia.minibilling;

import java.io.File;

public class FolderGenerator {

    public String generate(final User user, final String folderPathStr) {
        final String name = user.name();
        final String refNum = user.ref();
        final String pathname = folderPathStr + name + "-" + refNum;
        final File newFolder = new File(pathname);
        newFolder.mkdirs();
        return pathname;
    }
}
