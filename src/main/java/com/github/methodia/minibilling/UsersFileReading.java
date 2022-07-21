package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io. * ;

public class UsersFileReading implements FileReading {
    String path;

    public UsersFileReading(String path) {
        this.path = path;
    }

    public ArrayList<User> readToArrayList() {
        String line = "";
        ArrayList<User> arrListOfUserInformation=new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null)
            {
                String[] client = line.split(", ");
                arrListOfUserInformation.add(new User(client[0],client[1],Integer.parseInt(client[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrListOfUserInformation; }
}