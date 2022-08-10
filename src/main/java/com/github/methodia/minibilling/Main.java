package com.github.methodia.minibilling;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParseException, IOException, NoSuchFieldException, IllegalAccessException {
//        String reportDate = args[0];
//        String inPath = args[1];
//        String outPath = args[2];
        String reportDate = "21-03";
//        test1
//        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\input\\";
//        String outPath = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\expected\\";
//        main sample1
//        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
//        String outPath="C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\output\\";
//        main sample2
        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample2\\input\\";
        String outPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample2\\output\\";

        CSVUserReader userReader = new CSVUserReader(inPath);
        List<User> users = userReader.read();
        Map<String, User> userMap = CSVUserReader.getUserMap();
        CSVReadingReader readingReader = new CSVReadingReader(inPath);
        Collection<Reading> readingCollection = readingReader.read();

        for (int i = 1; i <= users.size(); i++) {
            User user = userMap.get(String.valueOf(i));
            List<Price> price = user.getPrice();
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
            Collection<Measurement> measurements = measurementGenerator.generate();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurements, price, reportDate);
            Invoice invoice = invoiceGenerator.generate();
            FolderGenerator folderGenerator = new FolderGenerator(user, outPath);
            String folderPath = folderGenerator.folderGenerate();
            JSONGenerator jsonGenerator = new JSONGenerator(invoice, folderPath);
            jsonGenerator.generateJSON();
        }
    }
}

