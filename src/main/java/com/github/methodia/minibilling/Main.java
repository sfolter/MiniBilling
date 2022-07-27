package com.github.methodia.minibilling;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        String userPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
        Users userReadingFile = new Users();
        ArrayList<String[]> usersList = userReadingFile.reader(userPath);
        FolderCreator foldersCreation = new FolderCreator();
        foldersCreation.createFolders();
        ArrayList<String> folderPath = foldersCreation.getFolderPath();
        String readingsPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
        Readings readingsReadFile = new Readings();
        ArrayList<String[]> readingsList = readingsReadFile.reader(readingsPath);
        ArrayList<Float> quantity1 = readingsReadFile.getQuantity();
        String pricesPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";
        Prices pricesReadFile = new Prices();
        ArrayList<String[]> priceList = pricesReadFile.reader(pricesPath);
        int docNum = 10000;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar cal = Calendar.getInstance();
        FileWriter file = null;
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < usersList.size(); i++) {
            String[] lineInReadings1  = readingsList.get(i);
            for (int j = readingsList.size() / 2; j < readingsList.size(); j++) {
                String[] lineInReadings = readingsList.get(i);
                float quantity = quantity1.get(i);
                lineInReadings = readingsList.get(j);
                String refReadsSecond = lineInReadings[0];
                if (userReadingFile.returnRefList().get(i).equals(refReadsSecond)) {
                    for (int k = 0; k < priceList.size(); k++) {
                        String[] lineInPrices = priceList.get(k);
                        String productInPrices = lineInPrices[0];
                        float price = Float.parseFloat(lineInPrices[3]);
                        if (readingsReadFile.getProduct().get(i).equals(productInPrices)) {
                            json.put("documentDate", dateFormat.format(cal.getTime()));
                            json.put("documentNumber", docNum);
                            json.put("consumer", userReadingFile.returnNameList().get(i));
                            json.put("reference", userReadingFile.returnRefList().get(i));
                            json.put("totalAmount", quantity*price);
                            JSONArray lines = new JSONArray();
                            json.put("lines", lines);
                            JSONObject newLine = new JSONObject();
                            newLine.put("index", 1);
                            newLine.put("quantity", quantity);
                            newLine.put("amount", quantity*price);
                            newLine.put("lineStart", lineInReadings1[2]);
                            newLine.put("lineEnd", lineInReadings[2]);
                            newLine.put("product",readingsReadFile.getProduct().get(i));
                            newLine.put("price", price);
                            newLine.put("priceList", userReadingFile.numOfPrice().get(i));
                            lines.add(newLine);
                            Date jud = new SimpleDateFormat("yy-MM-dd").parse(lineInReadings[2]);
                            String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
                            String[] splitDate = month.split("\\s+");
                            String monthInCyrilic = splitDate[1];
                            int year = Integer.parseInt(splitDate[2]) % 100;
                            String monthInUpperCase = monthInCyrilic.substring(0, 1).toUpperCase() + monthInCyrilic.substring(1);
                            String fileWriter = docNum + "-" + monthInUpperCase + "-" + year;
                            file = new FileWriter(folderPath.get(i) + "//" + fileWriter + ".json");
                            file.write(json.toJSONString());
                        }
                    }
                    docNum++;
                }
            }
            file.flush();
            file.close();
        }
    }
}

