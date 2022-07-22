package com.github.methodia.minibilling;

import java.io.File;
import java.util.ArrayList;

public class FolderCreator {
    public void createFolders() {
        String path = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\proba\\";
        Users proba1 = new Users();
        ArrayList<String> usernames = proba1.getNamesForFolders();

        ArrayList<String> referenten = proba1.getReferentenForFolders();


        for (int i = 0; i < usernames.size(); i++) {
            File f1 = new File(path + usernames.get(i) + "-" + referenten.get(i));
            boolean bool2 = f1.mkdirs();

        }
    }
}
