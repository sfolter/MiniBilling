package com.github.methodia.minibilling;

import org.json.simple.JSONArray;
import org.json.*;
import org.json.JSONObject;
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

    public static void main(String[] args) throws ParseException, IOException, NoSuchFieldException, IllegalAccessException {
//        Scanner scanner = new Scanner(System.in);
//        String userPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
//        Users userReadingFile = new Users();
//        ArrayList<String[]> usersList = userReadingFile.reader(userPath);
//        FolderCreator foldersCreation = new FolderCreator();
//        foldersCreation.createFolders();
//        ArrayList<String> folderPath = foldersCreation.getFolderPath();
//        String readingsPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
//        Readings readingsReadFile = new Readings();
//        ArrayList<String[]> readingsList = readingsReadFile.reader(readingsPath);
//        ArrayList<Float> quantity1 = readingsReadFile.getQuantity();
//        String pricesPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";
//        Prices pricesReadFile = new Prices();
//        ArrayList<String[]> priceList = pricesReadFile.reader(pricesPath);
//        int docNum = 10000;
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        Calendar cal = Calendar.getInstance();
//        FileWriter file = null;
//        JSONObject json = new JSONObject();
//        JSONObject newLine = new JSONObject();
//        Field changeMap = json.getClass().getDeclaredField("map");
//        changeMap.setAccessible(true);
//        changeMap.set(json, new LinkedHashMap<>());
//        changeMap.setAccessible(false);
//        Field changeMapArray = newLine.getClass().getDeclaredField("map");
//        changeMapArray.setAccessible(true);
//        changeMapArray.set(newLine, new LinkedHashMap<>());
//        changeMapArray.setAccessible(false);
//        for (int i = 0; i < usersList.size(); i++) {
//            String[] lineInReadings1  = readingsList.get(i);
//            for (int j = readingsList.size() / 2; j < readingsList.size(); j++) {
//                String[] lineInReadings = readingsList.get(j);
//                if (userReadingFile.returnRefList().get(i).equals(lineInReadings[0])) {
//                    for (int k = 0; k < priceList.size(); k++) {
//                        String[] lineInPrices = priceList.get(k);
//                        if (readingsReadFile.getProduct().get(i).equals(lineInPrices[0])) {
//                            json.put("documentDate", dateFormat.format(cal.getTime()));
//                            json.put("documentNumber", docNum);
//                            json.put("consumer", userReadingFile.returnNameList().get(i));
//                            json.put("reference", userReadingFile.returnRefList().get(i));
//                            json.put("totalAmount", quantity1.get(i)*Float.parseFloat(lineInPrices[3]));
//                            JSONArray lines = new JSONArray();
//                            newLine.put("index", 1);
//                            newLine.put("quantity", quantity1.get(i));
//                            newLine.put("amount", quantity1.get(i)*Float.parseFloat(lineInPrices[3]));
//                            newLine.put("lineStart", lineInReadings1[2]);
//                            newLine.put("lineEnd", lineInReadings[2]);
//                            newLine.put("product",readingsReadFile.getProduct().get(i));
//                            newLine.put("price", Float.parseFloat(lineInPrices[3]));
//                            newLine.put("priceList", userReadingFile.numOfPrice().get(i));
//                            lines.add(newLine);
//                            json.put("lines", lines);
//                            Date jud = new SimpleDateFormat("yy-MM-dd").parse(lineInReadings[2]);
//                            String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
//                            String[] splitDate = month.split("\\s+");
//                            String monthInCyrilic = splitDate[1];
//                            int year = Integer.parseInt(splitDate[2]) % 100;
//                            String monthInUpperCase = monthInCyrilic.substring(0, 1).toUpperCase() + monthInCyrilic.substring(1);
//                            String fileWriter = docNum + "-" + monthInUpperCase + "-" + year;
//                            file = new FileWriter(folderPath.get(i) + "//" + fileWriter + ".json");
//                            file.write(json.toString(4));
//                        }
//                    }
//                    docNum++;
//                }
//            }
//            file.flush();
//            file.close();
//        }
        CSVUserReader user = new CSVUserReader("C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv");
                user.read();
        CSVReadingsReader csv = new CSVReadingsReader("C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv");

        csv.read();
    }
}

