package com.github.methodia.minibilling;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
        Users userReadingFile = new Users();
        ArrayList<String[]> usersList = userReadingFile.reader(userPath);
        FolderCreator foldersCreation=new FolderCreator();
        foldersCreation.createFolders();
        String readingsPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
        Readings readingsReadFile = new Readings();
        readingsReadFile.reader(readingsPath);
        String pricesPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";
        Prices pricesReadFile = new Prices();
        pricesReadFile.reader(pricesPath);

        ArrayList<ZonedDateTime> parseToDate= readingsReadFile.dateParsing();
    }
}