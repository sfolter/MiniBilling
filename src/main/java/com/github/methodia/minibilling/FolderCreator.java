package com.github.methodia.minibilling;

import java.io.File;
import java.util.ArrayList;

public class FolderCreator extends Users {
    public ArrayList<String> folderPath=new ArrayList<>();


    public ArrayList<String> getFolderPath() {
        return folderPath;
    }

    public void createFolders() {
            String path = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\newFoldersForUsers\\";
            Users newUser = new Users();
            ArrayList<String> userNames = newUser.returnNameList();
            ArrayList<String> userRef = newUser.returnRefList();


            for (int m = 0; m < userNames.size(); m++) {
                folderPath.add( path + userNames.get(m) + "-" + userRef.get(m));
                File newFolder = new File(folderPath.get(m));
                boolean bool2 = newFolder.mkdirs();

            }

        }

}



