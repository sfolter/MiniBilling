package com.github.methodia.minibilling;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        String userPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
        Users userReadingFile = new Users();
        ArrayList<String[]> usersList = userReadingFile.reader(userPath);
        FolderCreator foldersCreation = new FolderCreator();
        foldersCreation.createFolders();
        ArrayList<String> folderPath = foldersCreation.getFolderPath();
        String readingsPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
        Readings readingsReadFile = new Readings();
        ArrayList<String[]> readingsList = readingsReadFile.reader(readingsPath);
        String pricesPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";
        Prices pricesReadFile = new Prices();
        ArrayList<String[]> priceList = pricesReadFile.reader(pricesPath);

        int p = 10000;
        float quantity = 0, amount = 0;
        FileWriter file = null;
        ArrayList<ZonedDateTime> parsedReadingsDate = readingsReadFile.dateParsing();
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < usersList.size(); i++) {
            String[] lineInUser = usersList.get(i);
            String name = lineInUser[0];
            int numOfPriceList = Integer.parseInt(lineInUser[2]);
            String refNum = lineInUser[1];
            String[] lineInReadings = readingsList.get(i);
            String refReadFirst = lineInReadings[0];
            float pokazanieFirst = Float.parseFloat(lineInReadings[3]);
            ZonedDateTime startLineDate = ZonedDateTime.parse(lineInReadings[2]);
            for (int j = readingsList.size() / 2; j < readingsList.size(); j++) {
                lineInReadings = readingsList.get(j);
                String refReadSecond = lineInReadings[0];
                String product = lineInReadings[1];
                ZonedDateTime endLineDate = ZonedDateTime.parse(lineInReadings[2]);
                float pokazanieSecond = Float.parseFloat(lineInReadings[3]);
                if (refNum.equals(refReadSecond)) {
                    for (int k = 0; k < priceList.size(); k++) {
                        String[] lineInPrices = priceList.get(k);
                        String productInPrices = lineInPrices[0];
                        LocalDate startDate = LocalDate.parse(lineInPrices[1]);
                        LocalDate endDate = LocalDate.parse(lineInPrices[2]);
                        float price = Float.parseFloat(lineInPrices[3]);


                        if (product.equals(productInPrices)) {
                            json.put("consumer", lineInUser[0]);
                            JSONArray lines = new JSONArray();
                            json.put("lines", lines);
                            JSONObject newLine = new JSONObject();
                            newLine.put("index", 1);
                            if (refReadFirst.equals(refReadSecond)) {
                                quantity = pokazanieSecond - pokazanieFirst;
                                amount = quantity * price;
                            }
                            newLine.put("quantity", quantity);
                            newLine.put("amount", amount);
                            newLine.put("lineStart", lineInReadings[2]);
                            newLine.put("lineEnd", lineInReadings[2]);
                            newLine.put("product", product);
                            newLine.put("price", price);
                            newLine.put("priceList", numOfPriceList);
                            lines.add(newLine);
                            Date jud = new SimpleDateFormat("yy-MM-dd").parse(lineInReadings[2]);
                            String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
                            String[] splitDate = month.split("\\s+");
                            String monthInCyrilic = splitDate[1];
                            int year = Integer.parseInt(splitDate[2]) % 100;
                            String monthInUpperCase = monthInCyrilic.substring(0, 1).toUpperCase() + monthInCyrilic.substring(1);
                            String fileWriter = p + "-" + monthInUpperCase + "-" + year;
                            file = new FileWriter(folderPath.get(i) + "//" + fileWriter + ".json");
                            file.write(json.toJSONString());
                        }

                    }
                    p++;
                }
            }
            file.flush();
            file.close();
            System.out.println(json);


        }
    }
}

