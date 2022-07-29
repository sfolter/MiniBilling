package com.github.methodia.minibilling;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, IllegalAccessException, NoSuchFieldException {
        String userPath = "C:\\Users\\Todor\\IdeaProjects\\MiniBilling-todor\\src\\test\\resources\\sample1\\input\\users.csv";
        Users user = new Users();
        user.reader(userPath);
        //FolderCreator
        FolderCreator foldersCreation = new FolderCreator();
        foldersCreation.createFolders();
        ArrayList<String> folderPath = foldersCreation.getFolderPath();
        //readings.csv
        String readingsPath = "C:\\Users\\Todor\\IdeaProjects\\MiniBilling-todor\\src\\test\\resources\\sample1\\input\\readings.csv";
        Readings reading = new Readings();
        reading.reader(readingsPath);
        ArrayList<Float> quantity = reading.getQuantity();
        //prices-1.csv
        String pricesPath = "C:\\Users\\Todor\\IdeaProjects\\MiniBilling-todor\\src\\test\\resources\\sample1\\input\\prices-1.csv";
        Prices price = new Prices();
        price.reader(pricesPath);

        // Current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar cal = Calendar.getInstance();


        int documentNumber = 10000;
        FileWriter file = null;
        //Json suzdavane
        for (int i = 0; i < user.getUserRefList().size(); i++) {
            JSONObject json = new JSONObject();
            JSONArray lines = new JSONArray();
            JSONObject newLine = new JSONObject();
            try {
                Field changeMap = json.getClass().getDeclaredField("map");
                changeMap.setAccessible(true);
                changeMap.set(json, new LinkedHashMap<>());
                changeMap.setAccessible(false);
                Field changeMapForArray = newLine.getClass().getDeclaredField("map");
                changeMapForArray.setAccessible(true);
                changeMapForArray.set(newLine, new LinkedHashMap<>());
                changeMapForArray.setAccessible(false);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                System.out.println((e.getMessage()));
            }


            json.put("consumer", user.getNameList().get(i));
            json.put("documentNumber", documentNumber);
            json.put("documentDate", dateFormat.format(cal.getTime()));
            json.put("reference", user.getUserRefList().get(i));
            json.put("totalAmount", "");
            //proverka po pricelist
            for (int j = 0; j < price.getPrice().size(); j++) {

                newLine.put("index", 1);
                newLine.put("quantity", quantity.get(i));
                newLine.put("lineStart", reading.getStartDateParsed().get(i));
                newLine.put("lineEnd", reading.getEndDateParsed().get(i));
                newLine.put("product", reading.getProduct().get(i));
                newLine.put("price", price.getPrice().get(j));
                newLine.put("priceList", user.getNumOfPriceList().get(i));
                newLine.put("amount", quantity.get(i) * price.getPrice().get(j));
            }
            lines.add(newLine);
            json.put("lines", lines);
            Date jud = new SimpleDateFormat("yy-MM-dd").parse(String.valueOf(reading.getEndDateParsed().get(i)));
            String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
            String[] splitDate = month.split("\\s+");
            String monthInCyrilic = splitDate[1];
            int year = Integer.parseInt(splitDate[2]) % 100;
            String monthInUpperCase = monthInCyrilic.substring(0, 1).toUpperCase() + monthInCyrilic.substring(1);
            String fileWriter = documentNumber + "-" + monthInUpperCase + "-" + year;
            file = new FileWriter(folderPath.get(i) + "//" + fileWriter + ".json");
            file.write(json.toString(4));

            documentNumber++;
            file.flush();
            file.close();
        }


    }
}
