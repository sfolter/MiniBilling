package com.github.methodia.minibilling;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        String reportTime;

//        String csvFileUsers = "src\\test\\resources\\sample1\\input\\users.csv";
//        String csvFileReports = "src\\test\\resources\\sample1\\input\\readings.csv";
        String csvFilePrices = "src\\test\\resources\\sample1\\input\\prices-1.csv";

        ClientReader csvUserReader = new ClientReader();
        ReadingsReader csvReportsReader = new ReadingsReader();
        PriceReader csvPriceReader = new PriceReader();

        ArrayList<Client> users = csvUserReader.read();

        Information inf = new Information();
        Lines line = new Lines();
        int counter = 10000;
        Map<String, List<Readings>> readings = csvReportsReader.read();
        Map<String, Price> prices = csvPriceReader.read(csvFilePrices);
        for (Client u : users) {

            List<Readings> readingsForUser = readings.get(u.getReferenceNumber());
            Readings firstReading = readings.get(u.getReferenceNumber()).get(0);
            Readings lastReading = readings.get(u.getReferenceNumber()).get(readingsForUser.size() - 1);
            prices.get("gas");


            Price price = prices.get(firstReading.getProduct());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

            inf.documentDate = ZonedDateTime.now().format(formatter);
            inf.documentDate = ZonedDateTime.now().format(formatter);
            inf.documentNumber = String.valueOf(counter);
            counter++;
            inf.consumer = u.getUserName();
            inf.reference = u.getReferenceNumber();
            line.price = price.getPrice();
            line.quantity = lastReading.getIndication() - firstReading.getIndication();
            line.amount = line.price * line.quantity;
            inf.totalAmount = line.amount;
            line.index = u.getPriceListNumber();
            line.lineStart = String.valueOf(firstReading.getDate());
            line.lineEnd = String.valueOf(lastReading.getDate());
            line.lineStart = String.valueOf(firstReading.getDate());
            line.lineEnd = String.valueOf(lastReading.getDate());
            line.product = firstReading.getProduct();
            line.priceList = u.getPriceListNumber();
            reportTime = String.valueOf(lastReading.getDate());

            inf.lines.add(line);





            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(inf);


            String folderPath = "src\\test\\resources\\sample1\\input" + "\\" + u.getUserName() + "-" + u.getReferenceNumber();
            File creatingFolders = new File(folderPath);
            Date date = new SimpleDateFormat("yy-MM-dd").parse(reportTime);
            String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(date);
            String[] splitDate = month.split("\\s+");
            String Cyrillic = splitDate[1];
            String fName = folderPath + "\\" + inf.getDocumentNumber() + "-" + Cyrillic + "-" + u.getReferenceNumber() + ".json";

            try {
                File directory = new File(String.valueOf(creatingFolders));
                directory.mkdirs();
                FileWriter myWriter = new FileWriter(fName);
                myWriter.write(json);
                myWriter.close();


            } catch (Exception e) {
                e.getStackTrace();


            }
            inf = new Information();
            line = new Lines();

        }

    }

}