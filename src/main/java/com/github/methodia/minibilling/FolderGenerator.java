package com.github.methodia.minibilling;

import java.io.File;

public class FolderGenerator {
    private User user;
    private String folderPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\w\\sample1\\test\\";

    public FolderGenerator(User user) {
        this.user = user;
    }

    public String generate() {
        User consumer = user;
        String name = consumer.getName();
        String refNum = consumer.getRef();
        String pathname = folderPath + name + "-" + refNum;
        File newFolder = new File(pathname);
        boolean bool2 = newFolder.mkdirs();
        return pathname;
    }
}
