package com.github.methodia.minibilling;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        String reportTime = "";

//        String csvFileUsers = "src\\test\\resources\\sample1\\input\\users.csv";
//        String csvFileReports = "src\\test\\resources\\sample1\\input\\readings.csv";
//        String csvFilePrices = "src\\test\\resources\\sample1\\input\\prices-1.csv";

        ClientReader csvUserReader = new ClientReader();
        ReadingsReader csvReportsReader = new ReadingsReader();
        PriceReader csvPriceReader = new PriceReader();

        ArrayList<Client> users = csvUserReader.read();

        Information inf = new Information();
        int counter = 10000;
        Map<String, List<Readings>> readings = csvReportsReader.read();
        for (Client u : users) {

            List<Readings> readingsForUser = readings.get(u.getReferenceNumber());
            Readings firstReading = readings.get(u.getReferenceNumber()).get(0);
            Readings lastReading = readings.get(u.getReferenceNumber()).get(readingsForUser.size() - 1);

            Lines line = new Lines();
            final String pricesReadingPath = "src\\test\\resources\\sample1\\input" + "\\" + "prices-" + u.getPriceListNumber() + ".csv";
            List<Price> prices1 = csvPriceReader.read(pricesReadingPath);

            for (Readings reading : readingsForUser) {

                for (Price p : prices1) {
                    if (reading.getProduct().equals(p.getProduct())) {

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                      //  Lines line = new Lines();
                        inf.documentDate = ZonedDateTime.now().format(formatter);
                        inf.documentDate = ZonedDateTime.now().format(formatter);
                        inf.documentNumber = String.valueOf(counter);
                        counter++;
                        inf.consumer = u.getUserName();
                        inf.reference = u.getReferenceNumber();
                        line.price = p.getPrice();
                        line.quantity = lastReading.getIndication() - firstReading.getIndication();
                        line.amount = line.price * line.quantity;
                        inf.totalAmount = line.amount;
                        line.index = u.getPriceListNumber();
                        line.lineStart = String.valueOf(p.getStartDate());
                        line.lineEnd = String.valueOf(p.getEndDate());
                        line.lineStart = String.valueOf(firstReading.getDate());
                        line.lineEnd = String.valueOf(lastReading.getDate());
                        line.product = reading.getProduct();
                        line.priceList = u.getPriceListNumber();
                        reportTime = String.valueOf(lastReading.getDate());

                        inf.lines.add(line);


                    }

                }
                Gson gson = new Gson();
                String json = gson.toJson(inf);
              //  System.out.println(json);
                Date d1 = new SimpleDateFormat("yy").parse(reportTime);

                String folderPath = "src\\test\\resources\\sample1\\input" + "\\" + u.getUserName() + "-" + u.getReferenceNumber();
                File creatingFolders = new File(folderPath);
                Date date = new SimpleDateFormat("yy-MM-dd").parse(reportTime);
                String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(date);
                String[] splitDate = month.split("\\s+");
                String Cyrillic = splitDate[1];
                String fName = folderPath  +"\\"+ inf.getDocumentNumber() + "-" + Cyrillic + "-" + u.getReferenceNumber() + ".json";
                File creatingFiles = new File(fName);
               // file.createNewFile();

                try {
                  File directory = new File(String.valueOf(creatingFolders));
                  directory.mkdirs();
                    PrintWriter out = new PrintWriter(new FileWriter(fName));
                        out.write(json.toString());


                } catch (Exception e) {
                    e.getStackTrace();


                }
//                try (PrintWriter out = new PrintWriter(new FileWriter(fName))) {
//                    out.write(json.toString());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                inf = new Information();

            }


        }


    }
}