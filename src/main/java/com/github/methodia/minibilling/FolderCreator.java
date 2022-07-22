package com.github.methodia.minibilling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderCreator extends Users {

        public void createFolders() {
            String path = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\newFoldersForUsers\\";
            Users newUser = new Users();
            ArrayList<String> userNames = newUser.returnNameList();
            ArrayList<String> userRef = newUser.returnRefList();

            for (int i = 0; i < userNames.size(); i++) {
                File newFolder = new File(path + userNames.get(i) + "-" + userRef.get(i));
                boolean bool2 = newFolder.mkdirs();

            }
        }

}


