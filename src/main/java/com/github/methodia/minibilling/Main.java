package com.github.methodia.minibilling;

import netscape.javascript.JSObject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

        Path resourceDirectory = Paths.get("src", "test", "resources", "sample1", "input");
        final String pricesReadingPath = resourceDirectory + "\\" + "prices-1.csv";
        final String readingsPath = resourceDirectory + "\\" + "readings.csv";

        final PricesFileReader pricesFileRead = new PricesFileReader();
        final ReadingsFileReader readingsRead = new ReadingsFileReader();
        UsersFileReading usersFileRead = new UsersFileReading();
        usersFileRead.convertUsersInformationToMap();
        pricesFileRead.convertPricesInformationToMap();
        readingsRead.convertReadingsInformationToMap(true);
        readingsRead.convertReadingsInformationToMap(false);
        System.out.println(readingsRead.convertReadingsInformationToMap(true));
        System.out.println(readingsRead.convertReadingsInformationToMap(false));


        }

    }




