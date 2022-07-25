package com.github.methodia.minibilling;

import com.google.gson.Gson;
import org.joda.time.DateTime;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        //WELCOME TO MINI BILLING!
        //csv file to read


        String csvFileUsers = "C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
        String csvFileReports = "C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
        String csvFilePrices = "C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";

        CSVUserReader csvUserReader = new CSVUserReader();
        CSVReportsReader csvReportsReader = new CSVReportsReader();
        CSVPriceReader csvPriceReader = new CSVPriceReader();

        //   csvReader.read(csvFile);
        csvUserReader.read();
        csvReportsReader.read();
        csvPriceReader.read(csvFilePrices);

        //    ArrayList<Users> user = new ArrayList<>();
//        for (int i = 0; i < csvUserReader.read().size(); i++) {
//            user.add(csvUserReader.read().get(i));
//         //   System.out.println(user);
////        }
//        ArrayList<List<Readings>> reports = new ArrayList<>();
//        for (int i = 0; i < csvReportsReader.read().size(); i++) {
//            reports.add(csvReportsReader.read().get(i));
//         //   System.out.println(reports);
//        }
//        ArrayList<Prices> prices = new ArrayList<>();
//        for (int i = 0; i < csvPriceReader.read(csvFilePrices).size(); i++) {
//            prices.add(csvPriceReader.read(csvFilePrices).get(i));
//         //   System.out.println(prices);
//        }


        ArrayList<Users> users = csvUserReader.read();
        Map<String, List<Readings>> readings = csvReportsReader.read();
        Information inf = new Information();
        for (Users u : users) {
            //  Information inf = new Information();
            List<Readings> readingsForUser = readings.get(u.getReferenceNumber());
            final String pricesReadingPath = "C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input" + "\\" + "prices-" + u.getPriceListNumber() + ".csv";
            List<Prices> prices1 = csvPriceReader.read(pricesReadingPath);
            for (Readings reading : readingsForUser) {
                for (Prices p : prices1) {

                    if (reading.getProduct().equals(p.getProduct())) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        inf.documentDate = ZonedDateTime.now().format(formatter);
                        inf.documentNumber = u.getReferenceNumber();
                        inf.consumer = u.getUserName();
                        inf.reference = u.getReferenceNumber();
                        inf.totalAmount = reading.getIndication();
                        Lines line = new Lines();
                        line.index = u.getPriceListNumber();
                        line.quantity = (int) Math.round(reading.getIndication() * 0.1 / p.getPrice());
                        line.lineStart = String.valueOf(p.getStartDate());
                        line.lineEnd = String.valueOf(p.getEndDate());
                        line.product = reading.getProduct();
                        line.price = p.getPrice();
                        line.priceList = u.getPriceListNumber();
                        line.amount = p.getPrice() * line.quantity;

                        inf.lines.add(line);
                        //  System.out.println(inf);

                        System.out.println(new Gson().toJson(inf));
                    }

                }

            }

        }


    }


}
