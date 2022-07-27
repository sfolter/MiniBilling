package com.github.methodia.minibilling;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.sound.sampled.Line;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

public class Main {
    @JsonPropertyOrder({"consumer", "documentDate", "documentNumber","reference","lines"})
    public static void main(String[] args) throws ParseException, IOException, IllegalAccessException, NoSuchFieldException {

        String userPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
        Users user = new Users();
        user.reader(userPath);
        FolderCreator foldersCreation = new FolderCreator();
        foldersCreation.createFolders();
        ArrayList<String> folderPath = foldersCreation.getFolderPath();
        String readingsPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
        Readings reading = new Readings();
        reading.reader(readingsPath);
        ArrayList<Float> quantity = reading.getQuantity();
        String pricesPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";
        Prices price = new Prices();
        price.reader(pricesPath);

        // Current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar cal = Calendar.getInstance();


        int documentNumber = 10000;
        FileWriter file = null;
        ArrayList<ZonedDateTime> parsedReadingsDate = reading.dateParsing();

        for (int i = 0; i < user.getUserRefList().size(); i++) {
//            Field changeMap = json.getClass().getDeclaredField("map");
//            changeMap.setAccessible(true);
//            changeMap.set(json,new LinkedHashMap<>());
//            changeMap.setAccessible(false);
            JSONObject json = new JSONObject();
            Lines lines = new Lines();
            JSONObject newLine = new JSONObject();

//            org.json.JSONArray courses = new org.json.JSONArray(new String[]{"index: " + user.getUserRefList().get(i), "quantity: " + reading.getQuantity().get(i),
//                    "lineStart: " + reading.getParsedData().get(i), "lineEnd: " + reading.getParsedData().get(i),
//                    "product: " + reading.getProduct().get(i), "price: " + price.getPrice().get(i),
//                    "priceList: " + user.getNumOfPriceList().get(i), "amount: " + reading.getReferentialNumberReadings().get(i)});
//            json.put("lines", courses);
//            if (reading.getProduct().get(i).equals(price.getProductInPrices()))

                json.put("consumer", user.getNameList().get(i));
                json.put("documentDate", dateFormat.format(cal.getTime()));
                json.put("documentNumber", documentNumber);
                json.put("reference", user.getUserRefList().get(i));

                json.put("lines", lines);

                newLine.put("index", 1);
                newLine.put("amount", quantity.get(i) * price.getPrice().get(0));
                newLine.put("quantity", quantity.get(i));
                newLine.put("lineStart", price.getParsedStartDate().get(0));
                newLine.put("lineEnd", price.getParsedEndDate().get(0));
                newLine.put("product", reading.getProduct().get(i));
                newLine.put("price", price.getPrice().get(0));
                newLine.put("priceList", user.getNumOfPriceList().get(i));
                lines.add(newLine);
                Date jud = new SimpleDateFormat("yy-MM-dd").parse(reading.getDataString().get(i));
                String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
                String[] splitDate = month.split("\\s+");
                String monthInCyrilic = splitDate[1];
                int year = Integer.parseInt(splitDate[2]) % 100;
                String monthInUpperCase = monthInCyrilic.substring(0, 1).toUpperCase() + monthInCyrilic.substring(1);
                String fileWriter = documentNumber + "-" + monthInUpperCase + "-" + year;
                file = new FileWriter(folderPath.get(i) + "//" + fileWriter + ".json");
                file.write(json.toJSONString());

            documentNumber++;
            file.flush();
            file.close();
        }


    }

}




