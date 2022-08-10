package com.github.methodia.minibilling;

import java.io.File;
import java.util.ArrayList;

public class FolderCreatorTodor {
    public ArrayList<String> folderPath = new ArrayList<>();


    public ArrayList<String> getFolderPath() {
        return folderPath;
    }

    public void createFolders() {
        String path = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\usernames\\";
        UsersTodor newUser = new UsersTodor();
        ArrayList<String> userNames = newUser.getNameList();
        ArrayList<String> userRef = newUser.getUserRefList();


        for (int m = 0; m < userNames.size(); m++) {
            folderPath.add(path + userNames.get(m) + "-" + userRef.get(m));
            File newFolder = new File(folderPath.get(m));
            boolean bool2 = newFolder.mkdirs();

        }

    }
}
