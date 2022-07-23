package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io. * ;

public class UsersFileReading implements FileReading {
    String path;
    final  Path resourceDirectory = Paths.get("src","test","resources","sample1","input");

    final  String usersFilePath=resourceDirectory+"\\" +"users.csv";

    public UsersFileReading() {
        this.path=usersFilePath;
    }

    public  Map<String, List<User>> convertUsersInformationToMap() {

        String line = "";
       Map<String, List<User>> mapOfUsersInformation=new LinkedHashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null)
            {
                String[] client = line.split(",");
                mapOfUsersInformation.putIfAbsent(client[1], new ArrayList<>());
                mapOfUsersInformation.get(client[1]).add(new User(client[0],client[1],Integer.parseInt(client[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapOfUsersInformation; }
}

