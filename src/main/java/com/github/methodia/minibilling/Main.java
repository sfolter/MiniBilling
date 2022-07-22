package com.github.methodia.minibilling;

import java.text.ParseException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        //Reading users.csv
        String userPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
        Users userReadingFile = new Users();
        ArrayList<String[]> usersList = userReadingFile.reader(userPath);
        //creating Folder
        FolderCreator foldersCreation = new FolderCreator();
        foldersCreation.createFolders();
        //reading readings.csv
        String readingsPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
        Readings readingsReadFile = new Readings();
        readingsReadFile.reader(readingsPath);

        ArrayList<Instant> proba1= readingsReadFile.parseToDate();
        for (int i = 0; i < proba1.size(); i++) {
            System.out.println(proba1.get(i));
        }

        //reading prices-1.csv
        String pricesPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";
        Prices pricesReadFile = new Prices();
        pricesReadFile.reader(pricesPath);
    }
}
