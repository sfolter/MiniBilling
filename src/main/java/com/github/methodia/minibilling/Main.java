package com.github.methodia.minibilling;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
//        final URL resource = Main.class.getClassLoader().getResource("");
//        final  String path = resource.getPath();
        Path resourceDirectory = Paths.get("src","test","resources","sample1","input");
        final String pricesReadingPath=resourceDirectory+"\\" +"prices-1.csv";
        final String readingsPath=resourceDirectory+"\\" +"readings.csv";

        final UsersFileReading usersFileRead = new UsersFileReading();

        final PricesFileReader pricesFileRead = new PricesFileReader(pricesReadingPath);

        final ReadingsFileReader readingsRead=new ReadingsFileReader(readingsPath);
        usersFileRead.readToArrayList();
        pricesFileRead.readToArrayList();
        readingsRead.readToArrayList();
        for (int i = 0; i <usersFileRead.readToArrayList().size() ; i++) {
            User user=usersFileRead.readToArrayList().get(i);
            for (int j = 0; j <readingsRead.readToArrayList().size() ; j++) {
                if(user)
            }
        }
        System.out.println(readingsRead.readToArrayList());




    }
}

