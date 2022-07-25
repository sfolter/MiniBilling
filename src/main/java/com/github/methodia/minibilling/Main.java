package com.github.methodia.minibilling;

import org.json.simple.JSONObject;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        String userPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
        Users userReadingFile = new Users();
        ArrayList<String[]> usersList = userReadingFile.reader(userPath);
        FolderCreator foldersCreation = new FolderCreator();
        foldersCreation.createFolders();
        String readingsPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
        Readings readingsReadFile = new Readings();
        ArrayList<String[]> readingsList = readingsReadFile.reader(readingsPath);
        String pricesPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";
        Prices pricesReadFile = new Prices();
        ArrayList<String[]> priceList = pricesReadFile.reader(pricesPath);


        ArrayList<ZonedDateTime> parsedReadingsDate = readingsReadFile.dateParsing();
        JSONObject json = new JSONObject();

        float quantity = 0;
        float totalAmount = 0;
        int i = 0, j = 0, k = 0;
        while (i < usersList.size()) {
            for (j = 0; j < readingsList.size(); j++) {
                if (userReadingFile.returnRefList().get(i).equals(readingsReadFile.getReferentialNumberReadings().get(j))) {
                    quantity = Math.abs(quantity - readingsReadFile.getPokazanie().get(j));
                    for (k = 0; k < priceList.size(); k++) {
                        if (pricesReadFile.getProductInPrices().get(k).equals(readingsReadFile.getProduct().get(j))) {
                            totalAmount = quantity*pricesReadFile.getPrice().get(k);
                            json.put("consumer", userReadingFile.returnNameList().get(i));
                        }

                    }


                }

                k = 0;
            }
            i++;
            j = 0;
//            System.out.println(totalAmount);
        }
        System.out.println(json);

    }
}